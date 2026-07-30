package com.app.reunite.controllers;

import com.app.reunite.entities.DTOs.ReunionDTO;
import com.app.reunite.entities.DTOs.ReunionRequest;
import com.app.reunite.entities.Reunion;
import com.app.reunite.entities.Usuario;
import com.app.reunite.services.ReunionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reuniones")
@RequiredArgsConstructor
public class ReunionController {
    private final ReunionService reunionService;

    @PostMapping("/crear")
    public ResponseEntity<ReunionDTO> crear(@RequestBody ReunionRequest request){
        return ResponseEntity.ok(reunionService.crearReunion(request));
    }

    @GetMapping("/visualizar-mis-reuniones")
    public ResponseEntity<List<ReunionDTO>> visualizarMisReuniones(){
        return ResponseEntity.ok(reunionService.visualizarMisReuniones());
    }

    @GetMapping("/visualizar-reuniones-participo")
    public ResponseEntity<List<ReunionDTO>> visualizarReunionesParticipo(){
        return ResponseEntity.ok(reunionService.visualizarReunionesParticipo());
    }


}
