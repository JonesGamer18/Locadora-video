package com.example.locadora.models;


import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "USUARIOS")
public class UsuarioModel extends RepresentationModel<UsuarioModel> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUsuario;
    private String nome;
    private String email;
    private String senha;
    private int QtdLocacoes;

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getQtdLocacoes() {
        return QtdLocacoes;
    }

    public void setQtdLocacoes(int qtdLocacoes) {
        QtdLocacoes = qtdLocacoes;
    }
}
