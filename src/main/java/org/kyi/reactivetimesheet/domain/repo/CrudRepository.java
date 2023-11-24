package org.kyi.reactivetimesheet.domain.repo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CrudRepository<T, ID>{
    Flux<T> findAll();
    Flux<T> findAllById(List<ID> ids);
    Mono<T> findById(ID id);
    Mono<T> save(T t);
    Mono<T> delete(ID id);
}
