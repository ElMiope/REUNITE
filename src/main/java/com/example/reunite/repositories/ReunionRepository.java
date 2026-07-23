package com.example.reunite.repositories;

import com.example.reunite.models.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion,Long> {
}
