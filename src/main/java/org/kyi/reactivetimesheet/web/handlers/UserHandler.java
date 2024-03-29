package org.kyi.reactivetimesheet.web.handlers;

import lombok.RequiredArgsConstructor;
import org.kyi.reactivetimesheet.domain.user.dto.CreateUserDto;
import org.kyi.reactivetimesheet.persistence.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var userId = serverRequest.pathVariable("id");
        return GlobalRoutingHandler.doRequest(userService.findById(userId), HttpStatus.OK);
    }

    public Mono<ServerResponse> findByUsername(ServerRequest serverRequest) {
        var username = serverRequest.pathVariable("username");
        return GlobalRoutingHandler.doRequest(userService.findByUsername(username), HttpStatus.OK);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        var createUserDtoMono = serverRequest.bodyToMono(CreateUserDto.class);
        return GlobalRoutingHandler.doRequest(userService.addUser(createUserDtoMono), HttpStatus.CREATED);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        var userId = serverRequest.pathVariable("id");
        return GlobalRoutingHandler.doRequest(userService.deleteUser(userId), HttpStatus.OK);
    }
}
