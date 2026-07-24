package com.app.reunite.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="amistades")
public class Amistades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amistad_id;
    @ManyToOne
    @JoinColumn(name = "usuario_1_id")
    private Usuario usuario1;
    @ManyToOne
    @JoinColumn(name = "usuario_2_id")
    private Usuario usuario2;
    private LocalDate fecha;
}
