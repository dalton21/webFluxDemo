package com.hastalasnubes.controller;

import com.hastalasnubes.model.Nodo;
import com.hastalasnubes.service.NodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/nodos")
public class NodoController {

    private NodoService nodoService;

    public NodoController(NodoService nodoService) {
        this.nodoService = nodoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Nodo> createNodo(@RequestBody Nodo nodo){
        return nodoService.saveNodo(nodo);
    }

    @GetMapping
    public Flux<Nodo> getAllNodos(){
        return nodoService.getAllNodos();
    }

    @GetMapping("{id}")
    public Mono<Nodo> getNodoById(@PathVariable("id") String nodoId){
        return nodoService.getNodoById(nodoId);
    }


}
