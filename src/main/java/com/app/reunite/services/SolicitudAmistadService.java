package com.app.reunite.services;

import com.app.reunite.entities.DTOs.SolicitudDTO;
import com.app.reunite.entities.SolicitudAmistad;
import com.app.reunite.entities.Usuario;
import com.app.reunite.enums.Estado_Solicitud;
import com.app.reunite.mapper.SolicitudMapper;
import com.app.reunite.repositories.SolicitudAmistadRepository;
import com.app.reunite.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudAmistadService {
    private final SolicitudAmistadRepository solicitudAmistadRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public SolicitudDTO enviarSolicitud(String usernameReceptor){
        Usuario emisor = usuarioService.getAuthenticatedUser();
        Usuario receptor = usuarioService.getUserByUsername(usernameReceptor);

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

    @Transactional
    public void cancelarSolicitud(Long id){
        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Solicitud no encontrada"));

        if(!solicitudAmistad.getUsuario_emisor().equals(usuarioService.getAuthenticatedUser()))
            throw new AuthorizationDeniedException("No tenes permiso para cancelar esta solicitud, ya que no te corresponde");

        solicitudAmistadRepository.deleteById(id);
    }

    @Transactional
    public SolicitudDTO aceptarSolicitud(Long id){
        Usuario usuario = usuarioService.getAuthenticatedUser();

        SolicitudAmistad solicitud = solicitudAmistadRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Solicitud no encontrada"));

        if(!solicitud.getUsuario_receptor().equals(usuario))
            throw new AuthorizationDeniedException("No tenes permiso de aceptar esta solicitud, debido a que no es una solicitud que te corresponda");

        solicitud.setEstado(Estado_Solicitud.ACEPTADA);

        solicitud.setFecha_respuesta(LocalDateTime.now());

        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.save(solicitud);

        return SolicitudMapper.toDTO(solicitudAmistad);
    }
    @Transactional
    public SolicitudDTO rechazarSolicitud(Long id){
        Usuario usuario = usuarioService.getAuthenticatedUser();

        SolicitudAmistad solicitud = solicitudAmistadRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Solicitud no encontrada"));

        if(!solicitud.getUsuario_receptor().equals(usuario))
            throw new AuthorizationDeniedException("No tenes permiso de rechazar esta solicitud, debido a que no es una solicitud que te corresponda");

        solicitud.setEstado(Estado_Solicitud.RECHAZADA);

        solicitud.setFecha_respuesta(LocalDateTime.now());

        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.save(solicitud);
        return SolicitudMapper.toDTO(solicitudAmistad);
    }


    public List<SolicitudDTO> visualizarSolicitudesRecibidas(){
        return solicitudAmistadRepository.findAll()
                .stream().filter(solicitud -> solicitud.getUsuario_receptor()
                        .equals(usuarioService.getAuthenticatedUser()))
                .map(SolicitudMapper::toDTO)
                .toList();
    }
    public List<SolicitudDTO> visualizarSolicitudesEnviadas(){
        return solicitudAmistadRepository.findAll()
                .stream().filter(solicitud -> solicitud.getUsuario_emisor()
                        .equals(usuarioService.getAuthenticatedUser()))
                .map(SolicitudMapper::toDTO)
                .toList();
    }

}
