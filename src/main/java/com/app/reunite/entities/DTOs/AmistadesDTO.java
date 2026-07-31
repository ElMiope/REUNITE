package com.app.reunite.entities.DTOs;

import java.time.LocalDate;

public record AmistadesDTO(
        Long id,
        Long usuario1_id,
        Long usuario2_id,
        LocalDate fecha
) {
}
