package com.example.locadora.controllers;

import com.example.locadora.dtos.FilmeRecordDto;
import com.example.locadora.dtos.UsuarioRecordDto;
import com.example.locadora.models.FilmeModel;
import com.example.locadora.models.UsuarioModel;
import com.example.locadora.repositories.FilmeRepository;
import com.example.locadora.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/user")
    public ResponseEntity<UsuarioModel> saveFilm(@RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        try {
            var usuarioModel = new UsuarioModel();
            BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));
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

    @GetMapping("/user")
    public  ResponseEntity<List<UsuarioModel>> getAllUsuarios(){
        List<UsuarioModel> usuarioList = usuarioRepository.findAll();
        if (!usuarioList.isEmpty()){
            for(UsuarioModel usuario: usuarioList){
                UUID id = usuario.getIdUsuario();
                usuario.add(linkTo(methodOn(UsuarioController.class).getOneUsuario(id)).withSelfRel());
            }

        }
        return  ResponseEntity.status(HttpStatus.OK).body(usuarioList);
    }
    @GetMapping("/user/{id}")
    public  ResponseEntity<Object> getOneUsuario(@PathVariable(value = "id") UUID id){
        Optional<UsuarioModel> usuarioO = usuarioRepository.findById(id);
        if(usuarioO.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        }
        usuarioO.get().add(linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("Lista de usuários"));
        return  ResponseEntity.status(HttpStatus.OK).body(usuarioO.get());

    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable(value="id")UUID id, @RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        Optional<UsuarioModel> usuarioO = usuarioRepository.findById(id);
        if(usuarioO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var usuarioModel = usuarioO.get();
        BeanUtils.copyProperties(usuarioRecordDto,usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuarioModel));
    }
    @DeleteMapping("/user/{id}")
    public  ResponseEntity<Object> deleteUsuario(@PathVariable(value = "id") UUID id) {
        Optional<UsuarioModel> usuarioO = usuarioRepository.findById(id);
        if (usuarioO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        usuarioRepository.delete(usuarioO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }
}
