package com.app.reunite.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        String error,
        String mensaje,
        LocalDateTime fecha,
        HttpStatus status
) {
}
