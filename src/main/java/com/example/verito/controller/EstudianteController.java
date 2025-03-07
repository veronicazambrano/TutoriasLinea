package com.example.verito.controller;

import com.example.verito.model.MaterialEstudio;
import com.example.verito.model.Tutor;
import com.example.verito.repository.MaterialEstudioRepository;
import com.example.verito.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class EstudianteController {

    @Autowired
    private MaterialEstudioRepository materialRepo;

    @Autowired
    private TutorRepository tutorRepo;

    // Mostrar materiales de estudio y tutores en la misma vista
    @GetMapping("/material-estudio")
    public String mostrarMaterialEstudio(Model model) {
        List<MaterialEstudio> materiales = materialRepo.findAll();
        List<Tutor> tutores = tutorRepo.findAll(); // Obtener todos los tutores
        model.addAttribute("materiales", materiales);
        model.addAttribute("tutores", tutores);
        return "estudiantes"; // Vista estudiantes.html
    }

    // Ruta para el formulario de subida de material de estudio
    @GetMapping("/subir-material-estudiante")
    public String mostrarFormularioSubidaMaterial(Model model) {
        model.addAttribute("materialEstudio", new MaterialEstudio());
        return "subir-material"; // Vista del formulario para subir material
    }

    // Procesar el formulario de subida de material
    @PostMapping("/subir-material-estudiante")
    public String subirMaterial(@ModelAttribute MaterialEstudio materialEstudio, @RequestParam("archivo") MultipartFile archivo, Model model) {
        try {
            materialEstudio.setArchivo(archivo.getBytes()); // Guardamos el archivo como un arreglo de bytes
            materialRepo.save(materialEstudio); // Guardar el material en la base de datos
            model.addAttribute("success", "Material subido exitosamente.");
        } catch (IOException e) {
            model.addAttribute("error", "Error al subir el material.");
        }
        return "redirect:/material-estudio"; // Redirigir a la lista de materiales después de subirlo
    }
}
