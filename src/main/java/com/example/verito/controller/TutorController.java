package com.example.verito.controller;

import com.example.verito.model.MaterialEstudio;
import com.example.verito.repository.MaterialEstudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class TutorController {

    @Autowired
    private MaterialEstudioRepository materialRepo;

    // Mostrar materiales de estudio para los tutores
    @GetMapping("/tutores")
    public String mostrarTutores(Model model) {
        List<MaterialEstudio> materiales = materialRepo.findAll();
        model.addAttribute("materiales", materiales);
        return "tutores"; // Vista de tutores.html
    }

    // Procesar el formulario de subida de material
    @PostMapping("/subir-material-tutor")
    public String subirMaterial(@RequestParam("titulo") String titulo,
                                @RequestParam("archivo") MultipartFile archivo, Model model) {
        try {
            if (archivo.isEmpty()) {
                model.addAttribute("error", "El archivo está vacío.");
                return "redirect:/tutores";
            }

            MaterialEstudio material = new MaterialEstudio();
            material.setTitulo(titulo); // Guarda el título del material
            material.setArchivo(archivo.getBytes()); // Guarda los bytes del archivo
            material.setNombreArchivo(archivo.getOriginalFilename()); // Guarda el nombre del archivo

            materialRepo.save(material);
            model.addAttribute("success", "Material subido exitosamente.");
        } catch (IOException e) {
            model.addAttribute("error", "Error al subir el material.");
        }
        return "redirect:/tutores";
    }
}
