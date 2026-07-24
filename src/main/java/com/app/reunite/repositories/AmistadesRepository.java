package com.app.reunite.repositories;

import com.app.reunite.entities.Amistades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmistadesRepository extends JpaRepository<Amistades,Long> {
}
