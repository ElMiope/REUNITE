package com.app.reunite.entities;

import com.app.reunite.enums.Rol;
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
    @JoinColumn(name="invitado_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private Rol rol;
}
