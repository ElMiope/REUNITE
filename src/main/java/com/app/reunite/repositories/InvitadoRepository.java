package com.app.reunite.repositories;

import com.app.reunite.entities.Invitado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitadoRepository extends JpaRepository<Invitado,Long> {
    Optional<Invitado> getInvitadoByUsuarioUsername(String username);
}
