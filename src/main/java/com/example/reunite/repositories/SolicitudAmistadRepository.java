package com.example.reunite.repositories;

import com.example.reunite.models.SolicitudAmistad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudAmistadRepository extends JpaRepository<SolicitudAmistad,Long> {
}
