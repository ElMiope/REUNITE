package com.example.reunite.services;

import com.example.reunite.exceptions.NotFoundException;
import com.example.reunite.exceptions.RepeatedEntityException;
import com.example.reunite.mappers.Mapper;
import com.example.reunite.models.DTOs.UsuarioDTO;
import com.example.reunite.models.Usuario;
import com.example.reunite.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private UsuarioDTO buscarUsuario(String username){
        return usuarioRepository.findByUsernameEqualsIgnoreCase(username).map(Mapper::toDTO).orElse(null);
    }
    private UsuarioDTO crear(Usuario usuario){
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

}
