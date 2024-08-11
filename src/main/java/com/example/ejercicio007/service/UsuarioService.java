package com.example.ejercicio007.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.ejercicio007.model.Usuario;

@Service
public class UsuarioService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Usuario getUsuarioById(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Nombre");
        usuario.setContraseña("123");
        usuario.setRol("ADMIN");
        return usuario;
    }
}
