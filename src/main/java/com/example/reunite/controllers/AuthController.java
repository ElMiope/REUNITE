package com.example.reunite.controllers;

import com.example.reunite.models.DTOs.UsuarioDTO;
import com.example.reunite.models.Usuario;
import com.example.reunite.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.crear(usuario));
    }
}
