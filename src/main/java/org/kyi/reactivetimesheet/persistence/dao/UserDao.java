package org.kyi.reactivetimesheet.persistence.dao;

import org.kyi.reactivetimesheet.persistence.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveMongoRepository<UserEntity, String> {
    Flux<UserEntity> findByTeamId(String teamId);
    Mono<UserEntity> findByUsername(String username);
}
