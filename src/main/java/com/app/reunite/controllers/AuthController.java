package com.app.reunite.controllers;

import com.app.reunite.entities.DTOs.LoginRequest;
import com.app.reunite.entities.DTOs.LoginResponse;
import com.app.reunite.entities.DTOs.RegisterRequest;
import com.app.reunite.entities.DTOs.UsuarioDTO;
import com.app.reunite.entities.Usuario;
import com.app.reunite.mapper.UsuarioMapper;
import com.app.reunite.services.JwtService;
import com.app.reunite.services.TokenBlackListService;
import com.app.reunite.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final TokenBlackListService tokenBlackListService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login){
        return ResponseEntity.ok(usuarioService.login(login));
    }
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest register){
        return ResponseEntity.ok(usuarioService.register(register));
    }

    @PostMapping("/me")
    public ResponseEntity<UsuarioDTO> me(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(UsuarioMapper.toDTO(usuario));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);

            long tiempoExpiracion = jwtService.getExpirationDate(token).getTime();
            tokenBlackListService.addToBlacklist(token, tiempoExpiracion);

            return ResponseEntity.ok("Sesión cerrada exitosamente.");
        }
        return ResponseEntity.badRequest().body("No se proporcionó un token válido.");
    }

}
