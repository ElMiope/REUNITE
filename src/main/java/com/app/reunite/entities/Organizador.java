package com.app.reunite.entities;

import com.app.reunite.enums.Rol;
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
    @Column(name="organizador_id")
    private Long id;
    @OneToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
    private Rol rol;
}