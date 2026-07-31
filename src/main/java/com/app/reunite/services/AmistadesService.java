package com.app.reunite.services;

import com.app.reunite.entities.Amistades;
import com.app.reunite.entities.DTOs.UsuarioDTO;
import com.app.reunite.entities.Usuario;
import com.app.reunite.exceptions.AmistadException;
import com.app.reunite.mapper.UsuarioMapper;
import com.app.reunite.repositories.AmistadesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmistadesService {
    private final AmistadesRepository amistadesRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public void crearAmistad(Amistades amistad){
        if(amistadesRepository.existsAmistadesByUsuario1OrUsuario2(amistad.getUsuario1(),amistad.getUsuario2()))
            throw new AmistadException("La amistad ya existe");
        Amistades a = amistadesRepository.save(amistad);
    }
    @Transactional
    public String eliminarAmistad(String username_amigo){
        Usuario usuarioLogeado = usuarioService.getAuthenticatedUser();
        Usuario usuarioAmigo = usuarioService.getUserByUsername(username_amigo);
        if(!amistadesRepository.existsAmistadesByUsuario1OrUsuario2(usuarioLogeado,usuarioAmigo))
            throw new AmistadException("La amistad no existe");
        Amistades amistad = amistadesRepository.findAmistadesByUsuario1_Username(usuarioLogeado.getUsername()).orElse(null);
        if(amistad==null)
            amistad = amistadesRepository.findAmistadesByUsuario2_Username(usuarioAmigo.getUsername()).orElse(null);
        // no puede ser nulo ya que si se llego hasta aca paso por el filtro de existsAmistadesByUsuario1OrUsuario2.
        // por lo tanto uno de los dos no va a retornar null en el orElse y va a continuar por mas que el IDE insista
        amistadesRepository.delete(amistad);
        return "Se elimino al usuario de tu lista de amigos";
    }

    public List<UsuarioDTO> visualizarMisAmigos(){
        Usuario usuarioLogeado = usuarioService.getAuthenticatedUser();
        List<Amistades> amistades = amistadesRepository.findAllAmistadesByUsername(usuarioLogeado);
        return amistades.stream().map(amistad -> {
                UsuarioDTO amigo;
                if(amistad.getUsuario1().equals(usuarioLogeado))
                    amigo = UsuarioMapper.toDTO(amistad.getUsuario2());
                else{
                    amigo = UsuarioMapper.toDTO(amistad.getUsuario1());
                }
                return amigo;
            })
                .toList();
    }
}
