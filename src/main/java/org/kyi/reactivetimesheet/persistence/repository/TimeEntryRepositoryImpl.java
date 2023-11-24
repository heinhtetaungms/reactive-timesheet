package org.kyi.reactivetimesheet.persistence.repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.kyi.reactivetimesheet.domain.time_entry.TimeEntry;
import org.kyi.reactivetimesheet.domain.time_entry.repository.TimeEntryRepository;
import org.kyi.reactivetimesheet.domain.user.User;
import org.kyi.reactivetimesheet.persistence.dao.TimeEntryDao;
import org.kyi.reactivetimesheet.persistence.exception.PersistenceException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeEntryRepositoryImpl implements TimeEntryRepository {
    private final TimeEntryDao timeEntryDao;

    @Override
    public Flux<TimeEntry> findAll() {
        return timeEntryDao.findAll()
                .flatMap(timeEntryEntity -> Mono.just(timeEntryEntity.toTimeEntry()));
    }

    @Override
    public Flux<TimeEntry> findAllById(List<String> ids) {
        return timeEntryDao.findAllById(ids)
                .flatMap(timeEntryEntity -> Mono.just(timeEntryEntity.toTimeEntry()));
    }

    @Override
    public Mono<TimeEntry> findById(String id) {
        return timeEntryDao.findById(id)
                .flatMap(timeEntryEntity -> Mono.just(timeEntryEntity.toTimeEntry()));
    }

    @Override
    public Mono<TimeEntry> save(TimeEntry timeEntry) {
        return timeEntryDao.save(timeEntry.toEntity())
                .flatMap(timeEntryEntity -> Mono.just(timeEntryEntity.toTimeEntry()));
    }

    @Override
    public Mono<TimeEntry> delete(String id) {
        return timeEntryDao.findById(id)
                .flatMap(timeEntryEntity -> timeEntryDao.delete(timeEntryEntity)
                        .then(Mono.just(timeEntryEntity.toTimeEntry())))
                .switchIfEmpty(Mono.error(new PersistenceException("cannot find time entry to delete by id " + id)));
    }

    @Override
    public Flux<TimeEntry> findAllByUser(User user) {
        return timeEntryDao.findAllByUser(user.toEntity())
                .flatMap(timeEntryEntity -> Mono.just(timeEntryEntity.toTimeEntry()));
    }
}
