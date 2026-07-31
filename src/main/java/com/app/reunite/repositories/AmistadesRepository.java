package com.app.reunite.repositories;

import com.app.reunite.entities.Amistades;
import com.app.reunite.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmistadesRepository extends JpaRepository<Amistades,Long> {
    boolean existsAmistadesByUsuario1OrUsuario2(Usuario usuario1 , Usuario usuario2);
    Optional<Amistades> findAmistadesByUsuario1_Username(String username);
    Optional<Amistades> findAmistadesByUsuario2_Username(String username);

    @Query("SELECT a FROM Amistades a WHERE a.usuario1 == :usuario OR a.usuario2 == :usuario ORDER BY a.fecha DESC")
    List<Amistades> findAllAmistadesByUsername(@Param("usuario") Usuario usuario);
}
