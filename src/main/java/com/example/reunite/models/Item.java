package com.example.reunite.models;

import com.example.reunite.enums.EUnidad;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.Pair;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;
    private String nombre;
    @Embedded // Le indica a JPA que este objeto forma parte de la tabla de la entidad
    @AttributeOverride(name = "left", column = @Column(name = "cantidad"))
    @AttributeOverride(name = "right", column = @Column(name = "unidad"))
    @Enumerated(EnumType.STRING)
    private Pair<Double, EUnidad> cantidad;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
