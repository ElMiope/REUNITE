package com.app.reunite.services;

import com.app.reunite.entities.DTOs.InvitacionDTO;
import com.app.reunite.entities.DTOs.InvitacionMapper;
import com.app.reunite.entities.Invitacion;
import com.app.reunite.entities.Invitado;
import com.app.reunite.entities.Reunion;
import com.app.reunite.entities.Usuario;
import com.app.reunite.enums.Estado_Solicitud;
import com.app.reunite.enums.Rol;
import com.app.reunite.repositories.InvitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitacionService {
    private final InvitacionRepository invitacionRepository;
    private final UsuarioService usuarioService;
    private final ReunionService reunionService;
    private final InvitadoService invitadoService;

    @Transactional
    public InvitacionDTO enviarInvitacion(String username,Long reunion_id){
        Usuario emisor = usuarioService.getAuthenticatedUser();
        Usuario receptor = usuarioService.getUserByUsername(username);

        Reunion reunion = reunionService.buscarReunion(reunion_id);

        Invitacion invitacion = Invitacion.builder()
                .usuario_emisor(emisor)
                .usuario_receptor(receptor)
                .reunion(reunion)
                .estado(Estado_Solicitud.PENDIENTE)
                .fecha_envio(LocalDateTime.now())
                .fecha_respuesta(null)
                .build();

        invitacion = invitacionRepository.save(invitacion);

        return InvitacionMapper.toDTO(invitacion);
    }

    @Transactional
    public void cancelarInvitacion(Long id){
        invitacionRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Invitacion no encontrada"));

        invitacionRepository.deleteById(id);
    }

    @Transactional
    public InvitacionDTO aceptarInvitacion(Long id){
        Usuario usuario = usuarioService.getAuthenticatedUser();
        Invitacion invitacion = invitacionRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Invitacion no encontrada"));

        if(!invitacion.getUsuario_emisor().equals(usuario))
            throw new AuthorizationDeniedException("No tenes permiso de aceptar esta invitacion, debido a que no es una invitacion que te corresponda");
        invitacion.setEstado(Estado_Solicitud.ACEPTADA);
        invitacion.setFecha_respuesta(LocalDateTime.now());

        invitacionRepository.save(invitacion);

        Invitado invitado = invitadoService.crearInvitado(usuario);

        reunionService.agregarInvitado(invitacion.getReunion().getId(),invitado);

        return InvitacionMapper.toDTO(invitacion);
    }

    @Transactional
    public InvitacionDTO rechazarInvitacion(Long id){
        Usuario usuario = usuarioService.getAuthenticatedUser();

        Invitacion invitacion = invitacionRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Invitacion no encontrada"));

        if(!invitacion.getUsuario_emisor().equals(usuario))
            throw new AuthorizationDeniedException("No tenes permiso de rechazar esta invitacion, debido a que no es una invitacion que te corresponda");
        invitacion.setEstado(Estado_Solicitud.RECHAZADA);
        invitacion.setFecha_respuesta(LocalDateTime.now());

        invitacionRepository.save(invitacion);

        return InvitacionMapper.toDTO(invitacion);
    }

    public List<InvitacionDTO> visualizarMisInvitacionesEnviadas(){
        return invitacionRepository.findAll().stream()
                .filter(invitacion -> invitacion
                        .getUsuario_emisor()
                        .equals(usuarioService.getAuthenticatedUser()))
                .map(InvitacionMapper::toDTO)
                .toList();
    }

    public List<InvitacionDTO> visualizarMisInvitacionesRecibidas(){
        return invitacionRepository.findAll().stream()
                .filter(invitacion -> invitacion
                        .getUsuario_receptor()
                        .equals(usuarioService.getAuthenticatedUser()))
                .map(InvitacionMapper::toDTO)
                .toList();
    }

}
