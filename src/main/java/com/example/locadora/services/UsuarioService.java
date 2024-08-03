package com.example.locadora.services;

import com.example.locadora.models.UsuarioModel;
import com.example.locadora.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioModel register(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean authenticate(String email, String senha) {
        Optional<UsuarioModel> userOptional = usuarioRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UsuarioModel usuario = userOptional.get();
            return passwordEncoder.matches(senha, usuario.getSenha());
        }
        return false;
    }
}
