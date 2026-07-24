package com.app.reunite.repositories;

import com.app.reunite.entities.SolicitudAmistad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudAmistadRepository extends JpaRepository<SolicitudAmistad,Long> {
}
