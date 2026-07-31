package com.app.reunite.entities;

import com.app.reunite.enums.TipoNotificacion;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notificacion_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String mensaje;
    private TipoNotificacion tipo;
}
