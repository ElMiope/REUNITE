package com.app.reunite.entities.DTOs;

import com.app.reunite.entities.Invitacion;
import com.app.reunite.entities.Reunion;
import com.app.reunite.entities.Usuario;
import com.app.reunite.services.ReunionService;
import com.app.reunite.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvitacionMapper {
    @Autowired
    private static UsuarioService usuarioService;
    @Autowired
    private static ReunionService reunionService;
    public static InvitacionDTO toDTO(Invitacion invitacion){
        return new InvitacionDTO(
                invitacion.getId(),
                invitacion.getUsuario_emisor().getUsername(),
                invitacion.getUsuario_receptor().getUsername(),
                invitacion.getReunion().getId(),
                invitacion.getEstado(),
                invitacion.getFecha_envio(),
                invitacion.getFecha_respuesta()
        );
    }
    public static Invitacion toEntity(InvitacionDTO invitacionDTO){
        Usuario emisor = usuarioService.getUserByUsername(invitacionDTO.username_emisor());
        Usuario receptor = usuarioService.getUserByUsername(invitacionDTO.username_receptor());
        Reunion reunion = reunionService.buscarReunion(invitacionDTO.reunion_id());
        return Invitacion.builder()
                .id(invitacionDTO.id())
                .usuario_emisor(emisor)
                .usuario_receptor(receptor)
                .reunion(reunion)
                .estado(invitacionDTO.estado())
                .fecha_envio(invitacionDTO.fecha_envio())
                .fecha_respuesta(invitacionDTO.fecha_respuesta())
                .build();
    }
}
