package com.hastalasnubes.service;

import com.hastalasnubes.model.Nodo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NodoService {
    Mono<Nodo> saveNodo(Nodo employee);
    Flux<Nodo> getAllNodos();
    Mono<Nodo> getNodoById(String id);
}
