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
    private Long reunion_id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private LocalDateTime fecha_hora;
    @ManyToOne
    @JoinColumn(name="organizador_id")
    private Organizador organizador;
    @JoinTable(name="invitados",joinColumns = @JoinColumn(name = "invidtado_id"),inverseJoinColumns = @JoinColumn(name="reunion_id"))
    private Set<Invitado> invitados;
}
