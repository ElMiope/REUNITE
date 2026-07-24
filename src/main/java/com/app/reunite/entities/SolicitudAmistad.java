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
@Table(name="solicitudes")
public class SolicitudAmistad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solicitud_id;
    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario usuario_emisor;
    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario usuario_recptor;
    private Estado_Solicitud estado;
    private LocalDateTime fecha_envio;
    private LocalDateTime fecha_respuesta;
}
