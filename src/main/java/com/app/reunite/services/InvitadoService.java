package com.app.reunite.services;

import com.app.reunite.entities.Invitado;
import com.app.reunite.entities.Usuario;
import com.app.reunite.enums.Rol;
import com.app.reunite.repositories.InvitadoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitadoService {
    private final InvitadoRepository invitadoRepository;

    @Transactional
    public Invitado crearInvitado(Usuario usuario){
        Invitado invitado = Invitado.builder()
                .usuario(usuario)
                .rol(Rol.INVITADO)
                .build();
        return invitadoRepository.save(invitado);
    }

    public Invitado getInvitadoByUsername(String username){
        return invitadoRepository.getInvitadoByUsuarioUsername(username).orElseThrow(()->new EntityNotFoundException("Invitado no encontrado"));
    }
}
