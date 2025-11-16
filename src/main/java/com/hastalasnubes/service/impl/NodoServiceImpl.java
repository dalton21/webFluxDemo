package com.hastalasnubes.service.impl;

import com.hastalasnubes.exception.ResourceNotFoundException;

import com.hastalasnubes.model.Nodo;
import com.hastalasnubes.repository.NodoRepository;
import com.hastalasnubes.service.NodoService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NodoServiceImpl implements NodoService {

    private final NodoRepository nodoRepository;

    public NodoServiceImpl(NodoRepository nodoRepository) {
        this.nodoRepository = nodoRepository;
    }

    @Override
    public Mono<Nodo> saveNodo(Nodo nodo) {
        return nodoRepository.findByTitulo(nodo.getTitulo())
                .flatMap(existing -> Mono.<Nodo>error(new ResourceNotFoundException("Nodo already exist with given title: " + nodo.getTitulo())))
                .switchIfEmpty(nodoRepository.save(nodo));
    }

    @Override
    public Flux<Nodo> getAllNodos() {
        return nodoRepository.findAll();
    }

    @Override
    public Mono<Nodo> getNodoById(long id) {
        return nodoRepository.findById(id);
    }

}
