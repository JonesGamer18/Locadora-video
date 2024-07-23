package com.example.locadora.repositories;

import com.example.locadora.models.FilmeModel;
import com.example.locadora.models.LocacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocacaoRepository extends JpaRepository<LocacaoModel, UUID> {
}
