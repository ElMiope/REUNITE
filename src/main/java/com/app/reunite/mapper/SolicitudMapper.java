package com.app.reunite.mapper;


import com.app.reunite.entities.DTOs.SolicitudDTO;
import com.app.reunite.entities.SolicitudAmistad;
import com.app.reunite.entities.Usuario;
import com.app.reunite.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SolicitudMapper {

    public static SolicitudDTO toDTO(SolicitudAmistad solicitudAmistad){
        return new SolicitudDTO(
                solicitudAmistad.getId(),
                solicitudAmistad.getUsuarioEmisor().getUsername(),
                solicitudAmistad.getUsuarioReceptor().getUsername(),
                solicitudAmistad.getEstado(),
                solicitudAmistad.getFechaEnvio(),
                solicitudAmistad.getFechaRespuesta()
        );
    }

}
