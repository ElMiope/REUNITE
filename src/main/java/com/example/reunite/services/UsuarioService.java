package com.example.reunite.services;

import com.example.reunite.exceptions.NotFoundException;
import com.example.reunite.exceptions.RepeatedEntityException;
import com.example.reunite.mappers.Mapper;
import com.example.reunite.models.DTOs.UsuarioDTO;
import com.example.reunite.models.Usuario;
import com.example.reunite.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private UsuarioDTO buscarUsuario(String username){
        return usuarioRepository.findByUsernameEqualsIgnoreCase(username).map(Mapper::toDTO).orElse(null);
    }
    public UsuarioDTO crear(Usuario usuario){
        UsuarioDTO uDto = buscarUsuario(usuario.getUsername());
        if(uDto != null) throw new RepeatedEntityException("El usuario ingresado ya existe");
        Usuario u = usuarioRepository.save(usuario);
        return Mapper.toDTO(u);
    }
    private UsuarioDTO actualizar(Usuario usuario){
        UsuarioDTO uDto = buscarUsuario(usuario.getUsername());
        if(uDto == null) throw new NotFoundException("El usuario ingresado no existe");
        Usuario u = usuarioRepository.save(usuario);
        return Mapper.toDTO(u);
    }
    private void eliminar(String username){
        UsuarioDTO u = buscarUsuario(username);
        if(u == null) throw new NotFoundException("El usuario no existe");
        usuarioRepository.deleteById(u.id());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsernameEqualsIgnoreCase(username).orElse(null);
        if(u == null) throw new UsernameNotFoundException("Usuario no enncontrado");
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        u.getRoles().forEach(rol -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRol().name()))));

        u.getRoles().stream()
                .flatMap(rol -> rol.getPermisos().stream())
                        .forEach(permiso ->
                                authorityList.add(new SimpleGrantedAuthority(
                                        permiso.getNombre())));
        return new User(
                u.getUsername(),
                u.getPassword(),
                u.isEnabled(),
                u.isAccountNonExpired(),
                u.isCredentialsNonExpired(),
                u.isAccountNonLocked(),
                authorityList
        );
    }
}
