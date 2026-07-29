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

    @Autowired
    private static UsuarioRepository usuarioRepository;

    public static SolicitudDTO toDTO(SolicitudAmistad solicitudAmistad){
        return new SolicitudDTO(
                solicitudAmistad.getId(),
                solicitudAmistad.getUsuario_emisor().getUsername(),
                solicitudAmistad.getUsuario_receptor().getUsername(),
                solicitudAmistad.getEstado(),
                solicitudAmistad.getFecha_envio(),
                solicitudAmistad.getFecha_respuesta()
        );
    }
    public static SolicitudAmistad toEntity(SolicitudDTO solicitudDTO){
        Usuario emisor = usuarioRepository.findByUsername(solicitudDTO.usernameEmisor())
                .orElseThrow(()->new UsernameNotFoundException("Usuario " + solicitudDTO.usernameEmisor() + " no encontrado"));
        Usuario receptor = usuarioRepository.findByUsername(solicitudDTO.usernameReceptor())
                .orElseThrow(()->new UsernameNotFoundException("Usuario " + solicitudDTO.usernameEmisor() + " no encontrado"));

        return SolicitudAmistad.builder()
                .id(solicitudDTO.id())
                .usuario_emisor(emisor)
                .usuario_receptor(receptor)
                .estado(solicitudDTO.estado())
                .fecha_envio(solicitudDTO.fecha_envio())
                .fecha_respuesta(solicitudDTO.fecha_respuesta())
                .build();
    }

}
