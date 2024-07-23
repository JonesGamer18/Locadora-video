package com.example.locadora.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="LOCACAO")
public class LocacaoModel extends RepresentationModel<LocacaoModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idLocacao;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private FilmeModel filmeId;

    public void setFilmeId(FilmeModel filmeId) {
        this.filmeId = filmeId;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    private  String nomeFilme;

    private double valorLocacao;

    public UUID getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(UUID idLocacao) {
        this.idLocacao = idLocacao;
    }

    public FilmeModel getFilmeId() {
        return filmeId;
    }

    public void setFilme(FilmeModel filmeId) {
        this.filmeId = filmeId;
    }


    public double getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(double valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

}
