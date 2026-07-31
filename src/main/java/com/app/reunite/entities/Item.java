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
@Table(name="items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long id;
    private String nombre;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "usuario_asignado_id")
    private Usuario usuarioAsignado;
}
