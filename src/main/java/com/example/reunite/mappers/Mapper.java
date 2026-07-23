package com.example.reunite.mappers;

import com.example.reunite.models.DTOs.UsuarioDTO;
import com.example.reunite.models.Usuario;

public class Mapper {
    public static UsuarioDTO toDTO(Usuario usuario){
        return new UsuarioDTO(
                usuario.getUsuario_id(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRoles()
        );
    }
}
