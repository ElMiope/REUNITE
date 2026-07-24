package com.app.reunite.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="invitados")
public class Invitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitado_id;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
