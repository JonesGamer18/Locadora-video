package com.example.locadora.dtos;

import java.util.UUID;

public class LocacaoRequest {
    private UUID filmeId;
    private double valorLocacao;
    private String nomeFilme;

    public UUID getFilme() {
        return filmeId;
    }

    public void setFilme(UUID filmeId) {
        this.filmeId = filmeId;
    }

    public double getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(double valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }
}
