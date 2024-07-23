package com.example.locadora.controllers;

import com.example.locadora.dtos.LocacaoRequest;
import com.example.locadora.models.FilmeModel;
import com.example.locadora.models.LocacaoModel;
import com.example.locadora.repositories.FilmeRepository;
import com.example.locadora.repositories.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LocacaoController {
    @Autowired
    LocacaoRepository locacaoRepository;

    @Autowired
    FilmeRepository filmeRepository;

    @PostMapping("/locacao")
    public ResponseEntity<String> criarLocacao(@RequestBody LocacaoRequest locacaoRequest) {
        Optional<FilmeModel> filmeOptional = filmeRepository.findById(locacaoRequest.getFilme());
        if (filmeOptional.isPresent()) {
            LocacaoModel locacao = new LocacaoModel();
            locacao.setFilme(filmeOptional.get());
            locacao.setValorLocacao(locacao.getValorLocacao());
            locacao.setNomeFilme(locacao.getNomeFilme());
            LocacaoModel locacaoModel = locacaoRepository.save(locacao);
            FilmeModel filme = filmeOptional.get();

            return ResponseEntity.status(HttpStatus.OK).body(filme.getNome() + " Alugado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
    }

}
