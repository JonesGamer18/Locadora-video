package com.example.locadora.controllers;

import com.example.locadora.dtos.AuthRequest;
import com.example.locadora.dtos.AuthResponse;
import com.example.locadora.models.UsuarioModel;
import com.example.locadora.services.BlacklistService;
import com.example.locadora.services.UsuarioService;
import com.example.locadora.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlacklistService blacklistService; // Adicione o serviço de blacklist

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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest, @RequestHeader(value = "Authorization", required = false) String existingToken) {
        // Verifica se o usuário já está logado
        if (existingToken != null && existingToken.startsWith("Bearer ")) {
            String jwtToken = existingToken.substring(7);
            String emailFromToken = jwtUtil.extractUsername(jwtToken);

            if (emailFromToken != null && jwtUtil.validateToken(jwtToken, emailFromToken)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse("Usuário já está logado!", jwtToken));
            }
        }

        // Processo normal de autenticação se o usuário não estiver logado
        if (usuarioService.authenticate(authRequest.getEmail(), authRequest.getSenha())) {
            // Gera o token JWT para o login bem-sucedido
            String token = jwtUtil.generateToken(authRequest.getEmail());

            // Retorna o token para o cliente
            return ResponseEntity.ok(new AuthResponse("Login bem-sucedido!", token));
        }

        // Retorna mensagem de falha na autenticação
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Falha na autenticação", null));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Adicionar o token à blacklist
        blacklistService.addTokenToBlacklist(jwtToken);

        return ResponseEntity.ok("Logout realizado com sucesso.");
    }

}
