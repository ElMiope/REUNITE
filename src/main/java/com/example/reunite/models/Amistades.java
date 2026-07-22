package com.example.reunite.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder @ToString
@Entity
public class Amistades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amistad_id;
    @ManyToOne
    private Usuario usuario1;
    @ManyToOne
    private Usuario usuario2;
    private LocalDateTime fecha_creacion;
}
