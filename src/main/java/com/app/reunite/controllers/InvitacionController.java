package com.app.reunite.controllers;

import com.app.reunite.entities.DTOs.InvitacionDTO;
import com.app.reunite.services.InvitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitaciones")
@RequiredArgsConstructor
public class InvitacionController {
    private final InvitacionService invitacionService;

    @PostMapping("/enviar-invitacion/{reunion_id}")
    public ResponseEntity<InvitacionDTO> enviarInvitacion(@PathVariable Long reunion_id, @RequestBody String username){
        return ResponseEntity.ok(invitacionService.enviarInvitacion(username, reunion_id));
    }
    @PostMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarInvitacion(@PathVariable Long id){
        invitacionService.cancelarInvitacion(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/aceptar/{id}")
    public ResponseEntity<InvitacionDTO> aceptarInvitacion(@PathVariable Long id){
        return ResponseEntity.ok(invitacionService.aceptarInvitacion(id));
    }
    @PutMapping("/rechazar/{id}")
    public ResponseEntity<InvitacionDTO> rechazarInvitacion(@PathVariable Long id){
        return ResponseEntity.ok(invitacionService.rechazarInvitacion(id));
    }
    @GetMapping("/mis-invitaciones")
    public ResponseEntity<List<InvitacionDTO>> visualizarMisInvitaciones(){
        return ResponseEntity.ok(invitacionService.visualizarMisInvitacionesEnviadas());
    }
    @GetMapping("/invitaciones-recibidas")
    public ResponseEntity<List<InvitacionDTO>> visualizarMisInvitacionesRecibidas(){
        return ResponseEntity.ok(invitacionService.visualizarMisInvitacionesRecibidas());
    }
}
