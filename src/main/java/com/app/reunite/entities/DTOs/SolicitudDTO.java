package com.app.reunite.entities.DTOs;

import com.app.reunite.enums.Estado_Solicitud;

import java.time.LocalDateTime;

public record SolicitudDTO(
        Long id,
        String usernameEmisor,
        String usernameReceptor,
        Estado_Solicitud estado,
        LocalDateTime fecha_envio,
        LocalDateTime fecha_respuesta
) {
}
