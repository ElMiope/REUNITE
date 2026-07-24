package com.app.reunite.repositories;

import com.app.reunite.entities.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador,Long> {
}
