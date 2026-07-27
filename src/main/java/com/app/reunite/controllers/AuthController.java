package com.app.reunite.controllers;

import com.app.reunite.entities.DTOs.LoginRequest;
import com.app.reunite.entities.DTOs.LoginResponse;
import com.app.reunite.entities.DTOs.RegisterRequest;
import com.app.reunite.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login){
        return ResponseEntity.ok(usuarioService.login(login));
    }
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest register){
        return ResponseEntity.ok(usuarioService.register(register));
    }
}
