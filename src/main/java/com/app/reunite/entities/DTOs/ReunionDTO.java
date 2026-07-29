package com.app.reunite.entities.DTOs;

import com.app.reunite.entities.Invitado;

import java.time.LocalDateTime;
import java.util.Set;

public record ReunionDTO(
        Long id,
        String nombre,
        String descripcion,
        String ubicacion,
        LocalDateTime fechaHora,
        Long organizadorId,
        Set<Invitado> invitados
) {
}
