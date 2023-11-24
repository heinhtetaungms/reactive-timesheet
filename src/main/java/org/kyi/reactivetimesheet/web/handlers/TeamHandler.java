package org.kyi.reactivetimesheet.web.handlers;

import lombok.RequiredArgsConstructor;
import org.kyi.reactivetimesheet.domain.team.dto.CreateTeamDto;
import org.kyi.reactivetimesheet.domain.team.dto.GetTeamDto;
import org.kyi.reactivetimesheet.persistence.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TeamHandler {
    private final TeamService teamService;

    public Mono<ServerResponse> findAllTeams(ServerRequest serverRequest) {
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(teamService.findAllTeams(), GetTeamDto.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var teamId = serverRequest.pathVariable("id");
        return GlobalRoutingHandler.doRequest(teamService.findById(teamId), HttpStatus.OK);
    }

    public Mono<ServerResponse> findByName(ServerRequest serverRequest) {
        var teamId = serverRequest.pathVariable("name");
        return GlobalRoutingHandler.doRequest(teamService.findByName(teamId), HttpStatus.OK);
    }

    public Mono<ServerResponse> addTeam(ServerRequest serverRequest) {
        var createTeamDtoMono = serverRequest.bodyToMono(CreateTeamDto.class);
        return GlobalRoutingHandler.doRequest(teamService.addTeam(createTeamDtoMono), HttpStatus.CREATED);
    }

    public Mono<ServerResponse> deleteTeam(ServerRequest serverRequest) {
        var teamId = serverRequest.pathVariable("id");
        return GlobalRoutingHandler.doRequest(teamService.deleteTeam(teamId), HttpStatus.OK);
    }
}
