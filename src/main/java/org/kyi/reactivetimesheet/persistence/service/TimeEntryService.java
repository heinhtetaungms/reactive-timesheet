package org.kyi.reactivetimesheet.persistence.service;

import org.kyi.reactivetimesheet.domain.time_entry.dto.CreateTimeEntryDto;
import org.kyi.reactivetimesheet.domain.time_entry.dto.GetTimeEntryDto;
import reactor.core.publisher.Mono;

public interface TimeEntryService {
    Mono<GetTimeEntryDto> addTimeEntry(Mono<CreateTimeEntryDto> createTimeEntryDtoMono);
    Mono<CreateTimeEntryDto> checkEntry(CreateTimeEntryDto timeEntryToCheck);
    Mono<CreateTimeEntryDto> findCollisions(CreateTimeEntryDto timeEntryToCheck);
}
