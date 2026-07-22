package com.example.reunite.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "reuniones")
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reunion_id;
    private String nombre;
    private Date fecha;
    private Time hora;
    private String ubicacion;
}
