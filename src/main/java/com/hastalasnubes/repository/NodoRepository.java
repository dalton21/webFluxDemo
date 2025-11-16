package com.hastalasnubes.repository;

import com.hastalasnubes.model.Nodo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface NodoRepository extends ReactiveCrudRepository<Nodo, Long> {

    Mono<Nodo> findByTitulo(String titulo);

}
