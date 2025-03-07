package com.example.verito.controller;

import com.example.verito.model.Usuario;
import com.example.verito.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Mostrar formulario de inicio de sesión
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario()); // Crear un nuevo objeto Usuario para el formulario
        return "login"; // Nombre de la vista (archivo .html)
    }

    // Procesar el formulario de inicio de sesión
    @PostMapping("/login")
    public String iniciarSesion(@ModelAttribute Usuario usuario, Model model) {
        Usuario usuarioAutenticado = usuarioService.iniciarSesion(usuario.getNombreUsuario(), usuario.getContraseña());

        if (usuarioAutenticado != null) {
            System.out.println("Rol del usuario autenticado: " + usuarioAutenticado.getRol()); // Depuración

            // Comparar ignorando mayúsculas/minúsculas
            if ("estudiante".equalsIgnoreCase(usuarioAutenticado.getRol())) {
                return "redirect:/material-estudio"; // Redirigir a la nueva ruta de estudiantes
            } else if ("tutor".equalsIgnoreCase(usuarioAutenticado.getRol())) {
                return "redirect:/tutores"; // Redirigir a la ruta correcta de tutores
            } else {
                model.addAttribute("error", "Rol de usuario no válido.");
                return "login";
            }
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
            return "login";
        }
    }

    // Ruta para mostrar el formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario()); // Crear un nuevo objeto Usuario para el formulario
        return "registro"; // Nombre de la vista (archivo .html)
    }

    // Procesar el formulario de registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        boolean exito = usuarioService.registrarUsuario(usuario); // Llamar al servicio para registrar al usuario
        if (exito) {
            return "redirect:/login"; // Redirigir al formulario de login después de registrar
        } else {
            model.addAttribute("error", "Error al registrar el usuario.");
            return "registro"; // Volver al formulario de registro
        }
    }
}
