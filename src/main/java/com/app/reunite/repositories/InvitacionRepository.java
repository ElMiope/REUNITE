package com.app.reunite.repositories;

import com.app.reunite.entities.Invitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitacionRepository extends JpaRepository<Invitacion,Long> {
}
