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
        String username = usuarioService.getUsername();
        Usuario usuario = usuarioService.getUserByUsername(username);
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

    private Reunion buscarReunion(Long id){
        return reunionRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Reunion no encontrada"));
    }

    public List<ReunionDTO> visualizarMisReuniones(){
        String username = usuarioService.getUsername();
        return reunionRepository.findAll()
                .stream()
                .filter(reunion -> reunion.getOrganizador()
                        .getUsuario()
                        .getUsername()
                        .equalsIgnoreCase(username))
                .map(ReunionMapper::toDTO)
                .toList();
    }

    public List<ReunionDTO> visualizarReunionesParticipo(){
        String username = usuarioService.getUsername();
        return reunionRepository.findAll()
                .stream()
                .filter(
                        reunion -> reunion
                                .getInvitados()
                                .stream().filter(invitado -> invitado
                                        .getUsuario()
                                        .getUsername()
                                        .equalsIgnoreCase(username))
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
        Set<Invitado> invitados = reunion.getInvitados();
        invitados.remove(invitado);
        reunion.setInvitados(invitados);
        reunionRepository.save(reunion);
    }

}
