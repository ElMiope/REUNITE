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
    @Column(name="invitacion_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario usuarioEmisor;
    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario usuarioReceptor;
    @ManyToOne
    @JoinColumn(name = "reunion_id")
    private Reunion reunion;
    private Estado_Solicitud estado;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaRespuesta;
}
