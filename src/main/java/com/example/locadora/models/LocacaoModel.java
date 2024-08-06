package com.example.locadora.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "LOCACAO")
public class LocacaoModel extends RepresentationModel<LocacaoModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idLocacao;

    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    private FilmeModel filme;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    private double valorLocacao;

    private String nomeFilme;

    public UUID getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(UUID idLocacao) {
        this.idLocacao = idLocacao;
    }

    public FilmeModel getFilme() {
        return filme;
    }

    public void setFilme(FilmeModel filme) {
        this.filme = filme;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
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
