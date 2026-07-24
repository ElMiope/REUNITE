package com.app.reunite.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Rol {
    USUARIO(Set.of(Permiso.CREAR_REUNIONES,Permiso.VISUALIZAR_REUNIONES)),
    INVITADO(Set.of(Permiso.VISUALIZAR_ITEMS,Permiso.VISUALIZAR_REUNIONES,Permiso.ASIGNAR_ITEM)),
    ORGANIZADOR(Set.of(
            Permiso.VISUALIZAR_REUNIONES,
            Permiso.MODIFICAR_REUNION,
            Permiso.ENVIAR_INVITACION,
            Permiso.CREAR_ITEMS,
            Permiso.VISUALIZAR_ITEMS,
            Permiso.ASIGNAR_ITEM
            ));
    private final Set<Permiso> permisos;

    Rol(Set<Permiso> permisos){
        this.permisos = permisos;
    }
    public Set<Permiso> getPermisos(){
        return permisos;
    }
    // Convierte tanto el Rol como sus Permisos en GrantedAuthorities
    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = permisos.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());

        // El rol debe llevar el prefijo ROLE_ por convención de Spring Security
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
