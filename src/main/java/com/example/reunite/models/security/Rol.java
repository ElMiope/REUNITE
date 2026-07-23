package com.example.reunite.models.security;

import com.example.reunite.enums.ERol;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rol_id;
    @Column(name="rol_nombre")
    @Enumerated(EnumType.STRING)
    private ERol rol;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="roles_permisos",joinColumns = @JoinColumn(name="rol_id"),inverseJoinColumns = @JoinColumn(name="permiso_id"))
    private Set<Permiso> permisos = new HashSet<>();
}
