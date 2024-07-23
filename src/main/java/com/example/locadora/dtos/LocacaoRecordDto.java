package com.example.locadora.dtos;

import com.example.locadora.models.FilmeModel;
import jakarta.validation.constraints.NotBlank;

public record LocacaoRecordDto(@NotBlank FilmeModel filme, @NotBlank double valor, @NotBlank String nomeFilme) {
}
