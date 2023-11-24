package org.kyi.reactivetimesheet.persistence.dao;

import org.kyi.reactivetimesheet.persistence.entity.TeamEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TeamDao extends ReactiveMongoRepository<TeamEntity, String> {
    Mono<TeamEntity> findByName(String name);
}
