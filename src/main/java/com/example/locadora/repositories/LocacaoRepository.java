package com.example.locadora.repositories;

import com.example.locadora.models.FilmeModel;
import com.example.locadora.models.LocacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LocacaoRepository extends JpaRepository<LocacaoModel, UUID> {
    Optional<LocacaoModel> findByFilme(FilmeModel filme);
}
