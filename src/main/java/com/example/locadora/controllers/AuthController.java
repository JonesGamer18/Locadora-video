package com.example.locadora.controllers;

import com.example.locadora.dtos.AuthRequest;
import com.example.locadora.dtos.AuthResponse;
import com.example.locadora.models.UsuarioModel;
import com.example.locadora.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioModel usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioService.register(usuario);  // Corrigido para chamar 'register'
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        if (usuarioService.authenticate(authRequest.getEmail(), authRequest.getSenha())) {
            return ResponseEntity.ok(new AuthResponse("Login bem-sucedido!"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Falha na autenticação"));
    }
}
