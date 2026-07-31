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
    public static ReunionDTO toDTO(Reunion reunion){
        return new ReunionDTO(
                reunion.getId(),
                reunion.getNombre(),
                reunion.getDescripcion(),
                reunion.getUbicacion(),
                reunion.getFechaHora(),
                reunion.getOrganizador().getId(),
                reunion.getInvitados()
        );
    }
}
