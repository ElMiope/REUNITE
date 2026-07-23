package com.example.reunite.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "reuniones")
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reunion_id;
    private String nombre;
    private Date fecha;
    private Time hora;
    private String ubicacion;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario creador;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="invitados_reuniones", joinColumns = @JoinColumn(name="reunion_id"),inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<Usuario> invitados;
}
