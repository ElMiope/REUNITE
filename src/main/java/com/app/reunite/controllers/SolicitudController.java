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
    public ResponseEntity<SolicitudDTO> enviarSolicitud(@RequestBody String usernameReceptor){
        return ResponseEntity.ok(solicitudAmistadService.enviarSolicitud(usernameReceptor));
    }
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        solicitudAmistadService.cancelarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/mis-solicitudes-recibidas")
    public ResponseEntity<List<SolicitudDTO>> misSolicitudesRecibidas(){
        return ResponseEntity.ok(solicitudAmistadService.visualizarSolicitudesRecibidas());
    }
    @GetMapping("/mis-solicitudes-enviadas")
    public ResponseEntity<List<SolicitudDTO>> misSolicitudesEnviadas(){
        return ResponseEntity.ok(solicitudAmistadService.visualizarSolicitudesEnviadas());
    }
    @PutMapping("/aceptar/{id}")
    public ResponseEntity<SolicitudDTO> aceptarSolicitud(@PathVariable Long id){
        return ResponseEntity.ok(solicitudAmistadService.aceptarSolicitud(id));
    }
    @PutMapping("/rechazar/{id}")
    public ResponseEntity<SolicitudDTO> rechazarSolicitud(@PathVariable Long id){
        return ResponseEntity.ok(solicitudAmistadService.rechazarSolicitud(id));
    }
}
