package com.app.reunite.services;

import com.app.reunite.entities.DTOs.ReunionDTO;
import com.app.reunite.entities.DTOs.ReunionRequest;
import com.app.reunite.entities.Invitado;
import com.app.reunite.entities.Organizador;
import com.app.reunite.entities.Reunion;
import com.app.reunite.entities.Usuario;
import com.app.reunite.enums.Rol;
import com.app.reunite.mapper.ReunionMapper;
import com.app.reunite.repositories.ReunionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReunionService {

    private final ReunionRepository reunionRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public ReunionDTO crearReunion(ReunionRequest request){
        Usuario usuario = usuarioService.getAuthenticatedUser();
        if(request.fechaHora().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("La fecha no puede ser anterior a la actual");
        Reunion reunion = Reunion.builder()
                        .nombre(request.nombre())
                        .descripcion(request.descripcion())
                        .ubicacion(request.ubicacion())
                        .fecha_hora(request.fechaHora())
                        .organizador(Organizador.builder().usuario(usuario).rol(Rol.ORGANIZADOR).build())
                .invitados(new HashSet<>())
                .build();

        reunion = reunionRepository.save(reunion);

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

    @Transactional
    public ReunionDTO modificarReunion(Long id, ReunionRequest request){
        Reunion reunion = buscarReunion(id);
        if(!reunion.getOrganizador().getUsuario().equals(usuarioService.getAuthenticatedUser()))
            throw new AuthorizationDeniedException("No tenes permiso para eliminar esta reunion, ya que no te corresponde");
        if(request.fechaHora().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("La fecha/hora no puede ser anterior a la fecha/hora actual");
        reunion.setNombre(request.nombre());
        reunion.setDescripcion(request.descripcion());
        reunion.setUbicacion(request.ubicacion());
        reunion.setFecha_hora(request.fechaHora());
        Reunion aux = reunionRepository.save(reunion);
        return ReunionMapper.toDTO(aux);
    }

    public Reunion buscarReunion(Long id){
        return reunionRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Reunion no encontrada"));
    }

    public List<ReunionDTO> visualizarMisReuniones(){
        return reunionRepository.findAll()
                .stream()
                .filter(reunion -> reunion.getOrganizador().getUsuario()
                        .equals(usuarioService.getAuthenticatedUser()))
                .map(ReunionMapper::toDTO)
                .toList();
    }

    public List<ReunionDTO> visualizarReunionesParticipo(){
        return reunionRepository.findAll()
                .stream()
                .filter(
                        reunion -> reunion
                                .getInvitados()
                                .stream().filter(invitado -> invitado.getUsuario()
                                        .equals(usuarioService.getAuthenticatedUser()))
                                .isParallel()
                )
                .map(ReunionMapper::toDTO)
                .toList();
    }

    @Transactional
    public void agregarInvitado(Long reunionId,Invitado invitado){
        Reunion reunion = buscarReunion(reunionId);
        Set<Invitado> invitados = reunion.getInvitados();
        invitados.add(invitado);
        reunion.setInvitados(invitados);
        reunionRepository.save(reunion);
    }
    @Transactional
    public void expulsarInvitado(Long reunionId,Invitado invitado){
        Reunion reunion = buscarReunion(reunionId);
        if(!reunion.getOrganizador().getUsuario().equals(usuarioService.getAuthenticatedUser()))
            throw new AuthorizationDeniedException("No tenes permiso para eliminar esta reunion, ya que no te corresponde");
        Set<Invitado> invitados = reunion.getInvitados();
        invitados.remove(invitado);
        reunion.setInvitados(invitados);
        reunionRepository.save(reunion);
    }

    public void eliminarReunion(Long id) {
        Reunion reunion = buscarReunion(id);
        if(!reunion.getOrganizador().getUsuario().equals(usuarioService.getAuthenticatedUser()))
            throw new AuthorizationDeniedException("No tenes permiso para eliminar esta reunion, ya que no te corresponde");
        reunionRepository.delete(reunion);
    }
}
