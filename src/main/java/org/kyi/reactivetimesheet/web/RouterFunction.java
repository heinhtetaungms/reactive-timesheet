package org.kyi.reactivetimesheet.web;

import org.kyi.reactivetimesheet.web.handlers.TeamHandler;
import org.kyi.reactivetimesheet.web.handlers.TimeEntryHandler;
import org.kyi.reactivetimesheet.web.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunction {

    // first argument of the router function is a request predicate, second - handler function
    @Bean
    public org.springframework.web.reactive.function.server.RouterFunction<ServerResponse> routerFunction(TeamHandler teamHandler, TimeEntryHandler timeEntryHandler, UserHandler userHandler) {
        return RouterFunctions
                .nest(path("/teams"), RouterFunctions
                                    .route(GET("/{name}").and(accept(MediaType.APPLICATION_JSON)), teamHandler::findByName)
                                    .andRoute(GET("/id/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandler::findById)
                                    .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), teamHandler::addTeam)
                                    .andRoute(DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandler::deleteTeam))
                .andNest(path("/users"), RouterFunctions
                                    .route(GET("/id/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::findById)
                                    .andRoute(GET("/{username}").and(accept(MediaType.APPLICATION_JSON)), userHandler::findByUsername)
                                    .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), userHandler::createUser)
                                    .andRoute(DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser))
                .andNest(path("/time-entries"), RouterFunctions
                                    .route(POST("/").and(accept(MediaType.APPLICATION_JSON)), timeEntryHandler::addTimeEntry));
    }
}
