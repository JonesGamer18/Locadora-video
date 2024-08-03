package com.example.locadora.controllers;

import com.example.locadora.models.FilmeModel;
import com.example.locadora.models.UsuarioModel;
import com.example.locadora.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/user")
    public ResponseEntity<List<UsuarioModel>> getAllUsers(){
        List<UsuarioModel> userList = usuarioRepository.findAll();
        if (!userList.isEmpty()){
            for(UsuarioModel user: userList){
                UUID id = user.getIdUsuario();
                user.add(linkTo(methodOn(UsuarioController.class).getOneUser(id)).withSelfRel());
            }

        }
        return  ResponseEntity.status(HttpStatus.OK).body(userList);
    }
    @GetMapping("/user/{id}")
    public  ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id){
        Optional<UsuarioModel> userO = usuarioRepository.findById(id);
        if(userO.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        }
        userO.get().add(linkTo(methodOn(UsuarioController.class).getAllUsers()).withRel("Lista de usuários"));
        return  ResponseEntity.status(HttpStatus.OK).body(userO.get());

    }
}
