package org.kyi.reactivetimesheet.web.handlers;

import lombok.RequiredArgsConstructor;
import org.kyi.reactivetimesheet.domain.time_entry.dto.CreateTimeEntryDto;
import org.kyi.reactivetimesheet.persistence.service.TimeEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TimeEntryHandler {
    private final TimeEntryService timeEntryService;

    public Mono<ServerResponse> addTimeEntry(ServerRequest serverRequest) {
        var createTimeEntryDtoMono = serverRequest.bodyToMono(CreateTimeEntryDto.class);
        return GlobalRoutingHandler.doRequest(timeEntryService.addTimeEntry(createTimeEntryDtoMono), HttpStatus.CREATED);
    }
}
