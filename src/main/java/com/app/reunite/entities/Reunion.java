package com.app.reunite.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="reuniones")
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reunion_id")
    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private LocalDateTime fechaHora;
    @ManyToOne
    @JoinColumn(name="organizador_id")
    private Organizador organizador;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="reunion_id")
    private Set<Invitado> invitados;
}
