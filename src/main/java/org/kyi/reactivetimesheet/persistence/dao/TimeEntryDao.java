package org.kyi.reactivetimesheet.persistence.dao;

import org.kyi.reactivetimesheet.persistence.entity.TimeEntryEntity;
import org.kyi.reactivetimesheet.persistence.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TimeEntryDao extends ReactiveMongoRepository<TimeEntryEntity, String> {
    Flux<TimeEntryEntity> findAllByUser(UserEntity user);
}
