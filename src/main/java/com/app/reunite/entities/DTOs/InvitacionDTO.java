package com.app.reunite.entities.DTOs;

import com.app.reunite.enums.Estado_Solicitud;

import java.time.LocalDateTime;

public record InvitacionDTO(
        Long id,
        String username_emisor,
        String username_receptor,
        Long reunion_id,
        Estado_Solicitud estado,
        LocalDateTime fecha_envio,
        LocalDateTime fecha_respuesta
) {
}
