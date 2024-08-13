package com.example.locadora.controllers;

import com.example.locadora.dtos.AuthRequest;
import com.example.locadora.dtos.AuthResponse;
import com.example.locadora.models.UsuarioModel;
import com.example.locadora.services.UsuarioService;
import com.example.locadora.utils.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioModel usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // Gera o token JWT para o usuário registrado
        String token = jwtUtil.generateToken(usuario.getEmail());
        usuario.setToken(token); // Armazena o token no banco de dados

        usuarioService.register(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso! Token: " + token);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        if (usuarioService.authenticate(authRequest.getEmail(), authRequest.getSenha())) {
            // Gera o token JWT para o login bem-sucedido
            String token = jwtUtil.generateToken(authRequest.getEmail());

            // Retorna o token para o cliente
            return ResponseEntity.ok(new AuthResponse("Login bem-sucedido!", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Falha na autenticação", null));
    }

}
