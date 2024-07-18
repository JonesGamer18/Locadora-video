package com.example.locadora.dtos;

import jakarta.validation.constraints.NotBlank;

public record FilmeRecordDto(@NotBlank String nome, @NotBlank String genero, @NotBlank String classificacaoIndicativa,Boolean disponibilidade) {
}
