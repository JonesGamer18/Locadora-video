package com.example.locadora.controllers;

import com.example.locadora.dtos.LocacaoRequest;
import com.example.locadora.models.FilmeModel;
import com.example.locadora.models.LocacaoModel;
import com.example.locadora.repositories.FilmeRepository;
import com.example.locadora.repositories.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/locacao")
public class LocacaoController {
    @Autowired
    LocacaoRepository locacaoRepository;

    @Autowired
    FilmeRepository filmeRepository;

    @PostMapping
    public ResponseEntity<String> criarLocacao(@RequestBody LocacaoRequest locacaoRequest) {
        Optional<FilmeModel> filmeOptional = filmeRepository.findById(locacaoRequest.getFilme());
        if (filmeOptional.isPresent()) {
            FilmeModel filmeModel = filmeOptional.get();

            if (Boolean.FALSE.equals(filmeModel.getDisponibilidade())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filme não está disponível para locação.");
            }

            LocacaoModel locacao = new LocacaoModel();
            locacao.setFilme(filmeModel);
            locacao.setValorLocacao(locacaoRequest.getValorLocacao());
            locacao.setNomeFilme(filmeModel.getNome());

            filmeModel.setDisponibilidade(false); // Atualiza a disponibilidade do filme para false
            filmeRepository.save(filmeModel); // Salva a atualização no banco de dados

            locacaoRepository.save(locacao);

            return ResponseEntity.status(HttpStatus.CREATED).body(filmeModel.getNome() + " alugado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
    }

    @PutMapping("/devolver/{filmeId}")
    public ResponseEntity<String> devolverFilme(@PathVariable UUID filmeId) {
        Optional<FilmeModel> filmeOptional = filmeRepository.findById(filmeId);
        if (filmeOptional.isPresent()) {
            FilmeModel filmeModel = filmeOptional.get();

            if (Boolean.TRUE.equals(filmeModel.getDisponibilidade())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filme já está disponível.");
            }

            filmeModel.setDisponibilidade(true); // Atualiza a disponibilidade do filme para true
            filmeRepository.save(filmeModel); // Salva a atualização no banco de dados

            // Aqui você pode decidir o que fazer com a locação: deletar ou marcar como devolvida
            // Vamos deletar a locação como exemplo
            Optional<LocacaoModel> locacaoOptional = locacaoRepository.findByFilme(filmeModel);
            locacaoOptional.ifPresent(locacaoRepository::delete);

            return ResponseEntity.status(HttpStatus.OK).body(filmeModel.getNome() + " devolvido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
    }
}
