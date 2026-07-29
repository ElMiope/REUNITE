package com.app.reunite.entities.DTOs;

import java.time.LocalDateTime;

public record ReunionRequest(
        String nombre,
        String descripcion,
        String ubicacion,
        LocalDateTime fechaHora
) {
}
