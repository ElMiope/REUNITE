package com.app.reunite.services;

import com.app.reunite.entities.DTOs.SolicitudDTO;
import com.app.reunite.entities.SolicitudAmistad;
import com.app.reunite.entities.Usuario;
import com.app.reunite.enums.Estado_Solicitud;
import com.app.reunite.mapper.SolicitudMapper;
import com.app.reunite.repositories.SolicitudAmistadRepository;
import com.app.reunite.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudAmistadService {
    private final SolicitudAmistadRepository solicitudAmistadRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitudDTO enviarSolicitud(String usernameEmisor,String usernameReceptor){
        Usuario emisor = usuarioRepository.findByUsername(usernameEmisor).orElseThrow(()->new UsernameNotFoundException("Usuario " + usernameReceptor + " no encontrado"));
        Usuario receptor = usuarioRepository.findByUsername(usernameReceptor).orElseThrow(()->new UsernameNotFoundException("Usuario " + usernameReceptor + " no encontrado"));

        SolicitudAmistad solicitud = SolicitudAmistad.builder()
                .usuario_emisor(emisor)
                .usuario_receptor(receptor)
                .estado(Estado_Solicitud.PENDIENTE)
                .fecha_envio(LocalDateTime.now())
                .fecha_respuesta(null)
                .build();

        solicitud = solicitudAmistadRepository.save(solicitud);

        return new SolicitudDTO(
                solicitud.getId(),
                solicitud.getUsuario_emisor().getUsername(),
                solicitud.getUsuario_receptor().getUsername(),
                solicitud.getEstado(),
                solicitud.getFecha_envio(),
                solicitud.getFecha_respuesta()
        );
    }

    public void cancelarSolicitud(Long id){
        solicitudAmistadRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Solicitud no encontrada"));

        solicitudAmistadRepository.deleteById(id);
    }

    public List<SolicitudDTO> visualizarSolicitudesRecibidas(String username){
        return solicitudAmistadRepository.findAll()
                .stream().filter(solicitud -> solicitud.getUsuario_receptor()
                        .getUsername()
                        .equalsIgnoreCase(username))
                .map(SolicitudMapper::toDTO)
                .toList();
    }
    public List<SolicitudDTO> visualizarSolicitudesEnviadas(String username){
        return solicitudAmistadRepository.findAll()
                .stream().filter(solicitud -> solicitud.getUsuario_emisor()
                        .getUsername()
                        .equalsIgnoreCase(username))
                .map(SolicitudMapper::toDTO)
                .toList();
    }

}
