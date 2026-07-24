package com.app.reunite.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="organizadores")
public class Organizador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizador_id;
    @OneToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
}