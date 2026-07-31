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
    @Column(name="solicitud_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario usuarioEmisor;
    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario usuarioReceptor;
    private Estado_Solicitud estado;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaRespuesta;
}
