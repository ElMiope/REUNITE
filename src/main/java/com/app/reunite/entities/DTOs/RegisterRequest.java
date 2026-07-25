package com.app.reunite.entities.DTOs;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}
