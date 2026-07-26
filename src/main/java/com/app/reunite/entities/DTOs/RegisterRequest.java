package com.app.reunite.entities.DTOs;

import jakarta.validation.constraints.Email;

public record RegisterRequest(
        String username,
        @Email
        String email,
        String password
) {
}
