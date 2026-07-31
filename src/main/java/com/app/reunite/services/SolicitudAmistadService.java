package com.app.reunite.services;

import com.app.reunite.entities.Amistades;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudAmistadService {
    private final SolicitudAmistadRepository solicitudAmistadRepository;
    private final UsuarioService usuarioService;
    private final AmistadesService amistadesService;

    @Transactional
    public SolicitudDTO enviarSolicitud(String usernameReceptor){
        Usuario emisor = usuarioService.getAuthenticatedUser();
        Usuario receptor = usuarioService.getUserByUsername(usernameReceptor);

        SolicitudAmistad solicitud = SolicitudAmistad.builder()
                .usuarioEmisor(emisor)
                .usuarioReceptor(receptor)
                .estado(Estado_Solicitud.PENDIENTE)
                .fechaEnvio(LocalDateTime.now())
                .fechaRespuesta(null)
                .build();

        solicitud = solicitudAmistadRepository.save(solicitud);

        return SolicitudMapper.toDTO(solicitud);
    }

    @Transactional
    public void cancelarSolicitud(Long id){
        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Solicitud no encontrada"));

        if(!solicitudAmistad.getUsuarioEmisor().equals(usuarioService.getAuthenticatedUser()))
            throw new AuthorizationDeniedException("No tenes permiso para cancelar esta solicitud, ya que no te corresponde");

        solicitudAmistadRepository.deleteById(id);
    }

    @Transactional
    public SolicitudDTO aceptarSolicitud(Long id){
        Usuario usuario = usuarioService.getAuthenticatedUser();

        SolicitudAmistad solicitud = solicitudAmistadRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Solicitud no encontrada"));

        if(!solicitud.getUsuarioReceptor().equals(usuario))
            throw new AuthorizationDeniedException("No tenes permiso de aceptar esta solicitud, debido a que no es una solicitud que te corresponda");

        solicitud.setEstado(Estado_Solicitud.ACEPTADA);

        solicitud.setFechaRespuesta(LocalDateTime.now());

        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.save(solicitud);

        amistadesService.crearAmistad(Amistades.builder()
                .usuario1(usuario)
                .usuario2(solicitud.getUsuarioReceptor())
                .fecha(LocalDate.now())
                .build()
        );

        return SolicitudMapper.toDTO(solicitudAmistad);
    }
    @Transactional
    public SolicitudDTO rechazarSolicitud(Long id){
        Usuario usuario = usuarioService.getAuthenticatedUser();

        SolicitudAmistad solicitud = solicitudAmistadRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Solicitud no encontrada"));

        if(!solicitud.getUsuarioReceptor().equals(usuario))
            throw new AuthorizationDeniedException("No tenes permiso de rechazar esta solicitud, debido a que no es una solicitud que te corresponda");

        solicitud.setEstado(Estado_Solicitud.RECHAZADA);

        solicitud.setFechaRespuesta(LocalDateTime.now());

        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.save(solicitud);
        return SolicitudMapper.toDTO(solicitudAmistad);
    }


    public List<SolicitudDTO> visualizarSolicitudesRecibidas(){
        return solicitudAmistadRepository.findAll()
                .stream().filter(solicitud -> solicitud.getUsuarioReceptor()
                        .equals(usuarioService.getAuthenticatedUser()))
                .map(SolicitudMapper::toDTO)
                .toList();
    }
    public List<SolicitudDTO> visualizarSolicitudesEnviadas(){
        return solicitudAmistadRepository.findAll()
                .stream().filter(solicitud -> solicitud.getUsuarioEmisor()
                        .equals(usuarioService.getAuthenticatedUser()))
                .map(SolicitudMapper::toDTO)
                .toList();
    }

}
