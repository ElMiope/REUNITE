package com.example.reunite.repositories;

import com.example.reunite.models.Invitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitacionRepository extends JpaRepository<Invitacion,Long> {
}
