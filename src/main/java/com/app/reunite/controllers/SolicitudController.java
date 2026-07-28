package com.app.reunite.controllers;

import com.app.reunite.entities.DTOs.SolicitudDTO;
import com.app.reunite.entities.Usuario;
import com.app.reunite.services.SolicitudAmistadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {
    private final SolicitudAmistadService solicitudAmistadService;

    @PostMapping("/enviar")
    public ResponseEntity<SolicitudDTO> enviarSolicitud(@AuthenticationPrincipal Usuario usuario, @RequestBody String usernameReceptor){
        return ResponseEntity.ok(solicitudAmistadService.enviarSolicitud(usuario.getUsername(),usernameReceptor));
    }
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        solicitudAmistadService.cancelarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/mis-solicitudes-recibidas")
    public ResponseEntity<List<SolicitudDTO>> misSolicitudesRecibidas(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(solicitudAmistadService.visualizarSolicitudesRecibidas(usuario.getUsername()));
    }
    @GetMapping("/mis-solicitudes-enviadas")
    public ResponseEntity<List<SolicitudDTO>> misSolicitudesEnviadas(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(solicitudAmistadService.visualizarSolicitudesEnviadas(usuario.getUsername()));
    }
}
