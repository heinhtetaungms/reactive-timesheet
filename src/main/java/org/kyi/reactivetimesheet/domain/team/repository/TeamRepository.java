package org.kyi.reactivetimesheet.domain.team.repository;

import org.kyi.reactivetimesheet.domain.repo.CrudRepository;
import org.kyi.reactivetimesheet.domain.team.Team;
import reactor.core.publisher.Mono;

public interface TeamRepository extends CrudRepository<Team, String> {
    Mono<Team> findByName(String name);
}
