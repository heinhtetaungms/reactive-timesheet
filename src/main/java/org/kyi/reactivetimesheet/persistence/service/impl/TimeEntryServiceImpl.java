package org.kyi.reactivetimesheet.persistence.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kyi.reactivetimesheet.domain.time_entry.TimeEntry;
import org.kyi.reactivetimesheet.domain.time_entry.TimeEntryUtils;
import org.kyi.reactivetimesheet.domain.time_entry.dto.CreateTimeEntryDto;
import org.kyi.reactivetimesheet.domain.time_entry.dto.GetTimeEntryDto;
import org.kyi.reactivetimesheet.domain.time_entry.repository.TimeEntryRepository;
import org.kyi.reactivetimesheet.domain.user.repository.UserRepository;
import org.kyi.reactivetimesheet.persistence.exception.TimeEntryServiceException;
import org.kyi.reactivetimesheet.persistence.exception.UserServiceException;
import org.kyi.reactivetimesheet.persistence.service.TimeEntryService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeEntryServiceImpl implements TimeEntryService {
    private final TimeEntryRepository timeEntryRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<GetTimeEntryDto> addTimeEntry(Mono<CreateTimeEntryDto> createTimeEntryDtoMono) {
        return createTimeEntryDtoMono
                .flatMap(this::checkEntry)
                .flatMap(createTimeEntryDto -> timeEntryRepository.save(createTimeEntryDto.toTimeEntry())
                        .map(TimeEntry::toGetTimeEntryDto));
    }

    @Override
    public Mono<CreateTimeEntryDto> checkEntry(CreateTimeEntryDto timeEntryToCheck) {
        return userRepository
                .findById(timeEntryToCheck.user().id())
                .hasElement()
                .flatMap(isUserPresent -> isUserPresent
                        ?
                        findCollisions(timeEntryToCheck)
                        :
                        Mono.error(new UserServiceException("user not exists")));
    }

    @Override
    public Mono<CreateTimeEntryDto> findCollisions(CreateTimeEntryDto timeEntryToCheck) {
        // TODO proper implementation of collision check (doesn't work at the moment). Create isAvailable() method in TimeEntry domain class

        return timeEntryRepository.findAllByUser(timeEntryToCheck.user().toUser())
                .filter(entry -> !TimeEntryUtils.toTimeFrom.apply(entry).isAfter(timeEntryToCheck.timeTo())
                        && !TimeEntryUtils.toTimeTo.apply(entry).isBefore(timeEntryToCheck.timeFrom()))
                .collectList()
                .flatMap(result -> {
                    if (result.isEmpty()) {
                        Mono.error(new TimeEntryServiceException("time entry collision"));
                    }
                    return Mono.just(timeEntryToCheck);
                });
    }
}
