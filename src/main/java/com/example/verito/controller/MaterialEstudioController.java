package com.example.verito.controller;

import com.example.verito.model.MaterialEstudio;
import com.example.verito.model.Tutor;
import com.example.verito.repository.MaterialEstudioRepository;
import com.example.verito.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/material")
public class MaterialEstudioController {

    @Autowired
    private MaterialEstudioRepository materialRepo;

    @Autowired
    private TutorRepository tutorRepo;

    // Ruta para subir materiales
    @PostMapping("/subir")
    public String subirMaterial(@RequestParam("titulo") String titulo,
                                @RequestParam("archivo") MultipartFile archivo,
                                @RequestParam("tutorId") Long tutorId, Model model) {
        try {
            Tutor tutor = tutorRepo.findByNombre("Veronica Zambrano");
            if (tutor == null) {
                tutor = new Tutor();
                tutor.setNombre("Veronica Zambrano");
                tutor.setArea("Programacion III");
                tutor = tutorRepo.save(tutor); // Guardar el tutor si no existe
            }


            // Obtener el nombre del archivo
            String nombreArchivo = archivo.getOriginalFilename();

            // Directorio donde se guardarán los archivos
            Path directorio = Paths.get("uploads");
            if (!Files.exists(directorio)) {
                Files.createDirectories(directorio);
            }

            // Ruta completa del archivo
            Path rutaArchivo = directorio.resolve(nombreArchivo);

            // Guardar el archivo en el directorio
            archivo.transferTo(rutaArchivo);

            // Crear y guardar el registro del material en la base de datos
            MaterialEstudio material = new MaterialEstudio();
            material.setTitulo(titulo);
            material.setNombreArchivo(nombreArchivo);
            material.setTutor(tutor);
            materialRepo.save(material);

            // Obtener los materiales después de subir el nuevo material
            List<MaterialEstudio> materiales = materialRepo.findByTutor(tutor);

            // Pasar los materiales al modelo para la vista
            model.addAttribute("materiales", materiales);

            return "redirect:/tutores"; // Redirigir a la vista de tutores con los materiales cargados
        } catch (IOException e) {
            return "redirect:/tutores"; // En caso de error, redirigir a la página de tutores
        }
    }

    // Ruta para obtener los materiales de estudio subidos por un tutor
    @GetMapping("/tutor/{tutorId}")
    public String obtenerMaterialesPorTutor(@PathVariable Long tutorId, Model model) {
        Tutor tutor = tutorRepo.findById(tutorId).orElse(null);
        if (tutor == null) {
            return "redirect:/tutores"; // Redirigir si no se encuentra el tutor
        }

        // Obtener todos los materiales subidos por el tutor
        List<MaterialEstudio> materiales = materialRepo.findByTutor(tutor);
        model.addAttribute("materiales", materiales);
        model.addAttribute("tutor", tutor);
        return "tutores"; // Asegúrate de que la vista 'tutores' exista y esté configurada correctamente
    }

    // Ruta para descargar el material de estudio
    @GetMapping("/descargar/{id}")
    public ResponseEntity<ByteArrayResource> descargarMaterial(@PathVariable Long id) {
        MaterialEstudio material = materialRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Material no encontrado"));

        try {
            // Ruta completa del archivo
            Path path = Paths.get("uploads").resolve(material.getNombreArchivo());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + material.getNombreArchivo())
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
