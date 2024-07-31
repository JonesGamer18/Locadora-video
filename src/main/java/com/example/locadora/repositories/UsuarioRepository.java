package com.example.locadora.repositories;

import com.example.locadora.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository  extends JpaRepository<UsuarioModel, UUID> {
}
