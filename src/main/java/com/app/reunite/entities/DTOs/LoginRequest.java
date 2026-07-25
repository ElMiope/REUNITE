package com.app.reunite.entities.DTOs;

public record LoginRequest(
        String username,
        String password
) {
}
