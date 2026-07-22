package com.example.reunite.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuario_id;
    @Column(unique = true)
    @NotBlank
    private String nombre;
    @Email(message = "fomato invalido")
    private String email;
    @Min(value = 4 ,message = "debe tener al menos 4 caracteres")
    private String password;
}
