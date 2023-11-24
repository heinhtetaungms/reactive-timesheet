package org.kyi.reactivetimesheet.persistence.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kyi.reactivetimesheet.domain.team.Team;
import org.kyi.reactivetimesheet.domain.team.TeamUtils;
import org.kyi.reactivetimesheet.domain.team.dto.CreateTeamDto;
import org.kyi.reactivetimesheet.domain.team.dto.GetTeamDto;
import org.kyi.reactivetimesheet.domain.team.repository.TeamRepository;
import org.kyi.reactivetimesheet.domain.user.repository.UserRepository;
import org.kyi.reactivetimesheet.persistence.exception.TeamServiceException;
import org.kyi.reactivetimesheet.persistence.service.TeamService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    public Flux<GetTeamDto> findAllTeams() {
        return teamRepository.findAll()
                .flatMap(team -> Flux.just(team.toGetTeamDto()));
    }

    @Override
    public Mono<GetTeamDto> findById(String teamId) {
        return teamRepository.findById(teamId)
                .map(Team::toGetTeamDto)
                .switchIfEmpty(Mono.error(new TeamServiceException("Team doesn't exist by team id : " + teamId)));
    }

    @Override
    public Mono<GetTeamDto> findByName(String name) {
        return teamRepository.findByName(name)
                .map(Team::toGetTeamDto)
                .switchIfEmpty(Mono.error(new TeamServiceException("Team doesn't exist by team name : " + name)));
    }

    @Override
    public Mono<GetTeamDto> addTeam(Mono<CreateTeamDto> createTeamDtoMono) {
        return createTeamDtoMono
                .flatMap(createTeamDto -> teamRepository.findByName(createTeamDto.name())
                        .doOnEach(team -> log.error("Team with name " + createTeamDto.name() + "already exists."))
                        .map(Team::toGetTeamDto)
                        .switchIfEmpty(Mono.defer(() -> createTeamWithMembers(createTeamDto))));
    }

    @Override
    public Mono<GetTeamDto> createTeamWithMembers(CreateTeamDto createTeamDto) {
        // at first team is inserted into db and all its member are updated with the new teamId
        return teamRepository
                .save(createTeamDto.toTeam())
                .flatMap(insertedTeam -> {
                    var membersToInsert = createTeamDto
                            .members()
                            .stream()
                            .map(createUserDto -> createUserDto.toUser().withTeamId(TeamUtils.toId.apply(insertedTeam)))
                            .toList();

                    // then all members with correct teamId are saved into db, flux is converted to list which is used to update team's members. At the end updated team is saved in db.
                    return userRepository
                            .saveAll(membersToInsert)
                            .collectList()
                            .flatMap(insertedUsers -> teamRepository
                                    .save(insertedTeam.withMembers(insertedUsers))
                                    .map(Team::toGetTeamDto));

                });
    }

    @Override
    public Mono<GetTeamDto> deleteTeam(String teamId) {
        return teamRepository
                .findById(teamId)
                .flatMap(team -> {

                    // at first, I'm changing teamId of all team members to null
                    var membersToUpdate = TeamUtils.toMembers.apply(team)
                            .stream()
                            .map(member -> member.withTeamId(null))
                            .toList();

                    // then I'm saving all updated members, deleting team and returning mono of DTO
                    return userRepository
                            .saveAll(membersToUpdate)
                            .then(teamRepository.delete(teamId))
                            .then(Mono.just(team.toGetTeamDto()));
                })
                .switchIfEmpty(Mono.error(new TeamServiceException("cannot find team to delete by team id : " + teamId)));
    }
}
