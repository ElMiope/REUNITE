package com.app.reunite.mapper;

import com.app.reunite.entities.DTOs.ReunionDTO;
import com.app.reunite.entities.Organizador;
import com.app.reunite.entities.Reunion;
import com.app.reunite.repositories.OrganizadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReunionMapper {
    @Autowired
    private static OrganizadorRepository organizadorRepository;

    public static ReunionDTO toDTO(Reunion reunion){
        return new ReunionDTO(
                reunion.getId(),
                reunion.getNombre(),
                reunion.getDescripcion(),
                reunion.getUbicacion(),
                reunion.getFecha_hora(),
                reunion.getOrganizador().getId(),
                reunion.getInvitados()
        );
    }
    public static Reunion toEntity(ReunionDTO reunionDTO){

        Organizador org = organizadorRepository.findById(reunionDTO.organizadorId())
                .orElseThrow(()->new EntityNotFoundException("Organizador no encontrado"));

        return Reunion.builder()
                .id(reunionDTO.id())
                .nombre(reunionDTO.nombre())
                .descripcion(reunionDTO.descripcion())
                .ubicacion(reunionDTO.ubicacion())
                .fecha_hora(reunionDTO.fechaHora())
                .organizador(org)
                .invitados(reunionDTO.invitados())
                .build();
    }
}
