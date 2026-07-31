package com.app.reunite.controllers;

import com.app.reunite.entities.DTOs.UsuarioDTO;
import com.app.reunite.services.AmistadesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amigos")
@RequiredArgsConstructor
public class AmistadesController {

    private final AmistadesService amistadesService;

    @DeleteMapping("/eliminar/{username}")
    public ResponseEntity<String> eliminarAmigo(@PathVariable String username){
        return ResponseEntity.ok(amistadesService.eliminarAmistad(username));
    }
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> visualizarListaAmigos(){
        return ResponseEntity.ok(amistadesService.visualizarMisAmigos());
    }
}
