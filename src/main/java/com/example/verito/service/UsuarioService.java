package com.example.verito.service;

import com.example.verito.model.Usuario;
import com.example.verito.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para registrar un usuario
    public boolean registrarUsuario(Usuario usuario) {
        try {
            usuarioRepository.save(usuario); // Guardar el usuario en la base de datos
            return true;
        } catch (Exception e) {
            return false; // Si ocurre algún error, retorna false
        }
    }

    // Método para iniciar sesión
    public Usuario iniciarSesion(String nombreUsuario, String contraseña) {
        // Busca el usuario por nombre de usuario
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario); // Cambiado aquí
        // Verifica si la contraseña coincide
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return usuario; // Si la autenticación es exitosa, retorna el usuario
        }
        return null; // Si el usuario no existe o la contraseña es incorrecta
    }
}
