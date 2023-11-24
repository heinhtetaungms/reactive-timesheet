package org.kyi.reactivetimesheet.persistence.service;

import org.kyi.reactivetimesheet.domain.team.dto.CreateTeamDto;
import org.kyi.reactivetimesheet.domain.team.dto.GetTeamDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TeamService {
    Flux<GetTeamDto> findAllTeams();
    Mono<GetTeamDto> findById(String teamId);
    Mono<GetTeamDto> findByName(String name);
    Mono<GetTeamDto> addTeam(Mono<CreateTeamDto> createTeamDtoMono);
    Mono<GetTeamDto> createTeamWithMembers(CreateTeamDto createTeamDto);
    Mono<GetTeamDto> deleteTeam(String teamId);
}
