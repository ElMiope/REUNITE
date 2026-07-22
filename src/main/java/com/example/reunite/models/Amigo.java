package com.example.reunite.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "amigo")
public class Amigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amigo_id;
    @ManyToOne
    private Usuario usuario;
}
