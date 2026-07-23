package com.example.reunite.repositories;

import com.example.reunite.models.Amistades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmistadesRepository extends JpaRepository<Amistades,Long> {
}
