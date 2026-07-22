package com.example.reunite.models;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "organizadores")
public class Organizador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizador_id;
    @OneToOne
    private Usuario usuario;
}
