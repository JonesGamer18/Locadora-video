package com.example.locadora.controllers;

import com.example.locadora.dtos.FilmeRecordDto;
import com.example.locadora.models.FilmeModel;
import com.example.locadora.repositories.FilmeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class FilmeController {

    @Autowired
    FilmeRepository filmeRepository;

    @PostMapping("/film")
    public ResponseEntity<FilmeModel> saveFilm(@RequestBody @Valid FilmeRecordDto filmeRecordDto) {
        try {
            var filmeModel = new FilmeModel();
            BeanUtils.copyProperties(filmeRecordDto, filmeModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmeRepository.save(filmeModel));
        } catch (Exception e) {
            // Log the exception (use a logging framework like Logback or Log4j)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        // Log the exception (use a logging framework like Logback or Log4j)
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @GetMapping("/film")
    public  ResponseEntity<List<FilmeModel>> getAllFilmes(){
        List<FilmeModel> filmeList = filmeRepository.findAll();
        if (!filmeList.isEmpty()){
            for(FilmeModel filme: filmeList){
                UUID id = filme.getIdFilme();
                filme.add(linkTo(methodOn(FilmeController.class).getOneFilme(id)).withSelfRel());
            }

        }
        return  ResponseEntity.status(HttpStatus.OK).body(filmeList);
    }
    @GetMapping("/film/{id}")
    public  ResponseEntity<Object> getOneFilme(@PathVariable(value = "id") UUID id){
        Optional<FilmeModel> filmeO = filmeRepository.findById(id);
        if(filmeO.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");

        }
        filmeO.get().add(linkTo(methodOn(FilmeController.class).getAllFilmes()).withRel("Lista de filmes"));
        return  ResponseEntity.status(HttpStatus.OK).body(filmeO.get());

    }

    @PutMapping("/film/{id}")
    public ResponseEntity<Object> updateFilme(@PathVariable(value="id")UUID id, @RequestBody @Valid FilmeRecordDto filmeRecordDto) {
        Optional<FilmeModel> filmeO = filmeRepository.findById(id);
        if(filmeO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
        }
        var filmeModel = filmeO.get();
        BeanUtils.copyProperties(filmeRecordDto,filmeModel);
        return ResponseEntity.status(HttpStatus.OK).body(filmeRepository.save(filmeModel));
    }
    @DeleteMapping("/film/{id}")
    public  ResponseEntity<Object> deleteFilme(@PathVariable(value = "id") UUID id){
        Optional<FilmeModel> filmeO = filmeRepository.findById(id);
        if(filmeO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
        }
        filmeRepository.delete(filmeO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Filme deletado com sucesso");

    }
}
