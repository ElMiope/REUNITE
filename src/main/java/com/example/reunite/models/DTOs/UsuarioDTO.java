package com.example.reunite.models.DTOs;

import com.example.reunite.models.security.Rol;

import java.util.Set;

public record UsuarioDTO(
        Long id,
        String username,
        String email,
        Set<Rol> roles
) {
}
