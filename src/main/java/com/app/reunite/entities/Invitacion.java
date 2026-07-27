package com.app.reunite.entities;

import com.app.reunite.enums.Estado_Solicitud;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="invitaciones")
public class Invitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name="invitacion_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario usuario_emisor;
    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario usuario_receptor;
    @ManyToOne
    @JoinColumn(name = "reunion_id")
    private Reunion reunion;
    private Estado_Solicitud estado;
    private LocalDateTime fecha_envio;
    private LocalDateTime fecha_respuesta;
}
