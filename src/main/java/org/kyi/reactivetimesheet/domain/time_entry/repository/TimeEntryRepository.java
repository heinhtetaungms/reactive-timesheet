package org.kyi.reactivetimesheet.domain.time_entry.repository;

import org.kyi.reactivetimesheet.domain.repo.CrudRepository;
import org.kyi.reactivetimesheet.domain.time_entry.TimeEntry;
import org.kyi.reactivetimesheet.domain.user.User;
import reactor.core.publisher.Flux;

public interface TimeEntryRepository extends CrudRepository<TimeEntry, String> {
    Flux<TimeEntry> findAllByUser(User user);
}
