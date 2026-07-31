package com.app.reunite.mapper;

import com.app.reunite.entities.Amistades;
import com.app.reunite.entities.DTOs.AmistadesDTO;

public class AmistadesMapper {
    public static AmistadesDTO toDTO(Amistades amistades){
        return new AmistadesDTO(
                amistades.getId(),
                amistades.getUsuario1().getId(),
                amistades.getUsuario2().getId(),
                amistades.getFecha()
        );
    }
}
