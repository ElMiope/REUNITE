package com.app.reunite.services;

import com.app.reunite.entities.DTOs.LoginRequest;
import com.app.reunite.entities.DTOs.LoginResponse;
import com.app.reunite.entities.DTOs.RegisterRequest;
import com.app.reunite.entities.DTOs.UsuarioDTO;
import com.app.reunite.entities.Usuario;
import com.app.reunite.enums.Rol;
import com.app.reunite.mapper.UsuarioMapper;
import com.app.reunite.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService,
                          @Lazy AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public Usuario getUserByUsername(String username){
        return usuarioRepository.findByUsername(username).orElseThrow(()->new EntityNotFoundException("Usuario " + username + " no encontrado"));
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Usuario " + username + " no enconntrado"));
    }

    //este es el que se usa en el endpoint
    public UsuarioDTO findByUsername(String username){
        return UsuarioMapper.toDTO(usuarioRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario " + username + " no encontrado")));
    }

    @Transactional
    public LoginResponse register(RegisterRequest request){
        if(usuarioRepository.existsByUsername(request.username()))
            throw new IllegalArgumentException("El nombre de usuario ya esta registrado");
        if(usuarioRepository.existsByEmail(request.email()))
            throw new IllegalArgumentException("El email ya esta registrado");
        Usuario usuario = Usuario.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .rol(Rol.USUARIO)
                .build();

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);
        return new LoginResponse(token);
    }
    public LoginResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),request.password())
        );
        Usuario usuario = getUserByUsername(request.username());
        String token = jwtService.generateToken(usuario);
        return new LoginResponse(token);
    }


}
