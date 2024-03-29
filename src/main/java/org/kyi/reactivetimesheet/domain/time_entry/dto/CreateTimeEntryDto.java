package org.kyi.reactivetimesheet.domain.time_entry.dto;

import org.kyi.reactivetimesheet.domain.time_entry.TimeEntry;
import org.kyi.reactivetimesheet.domain.time_entry.type.Category;
import org.kyi.reactivetimesheet.domain.user.dto.GetUserDto;

import java.time.LocalDateTime;

public record CreateTimeEntryDto(LocalDateTime timeFrom, LocalDateTime timeTo, GetUserDto user, Category category, String description) {
    public TimeEntry toTimeEntry() {
        return TimeEntry.builder()
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .user(user.toUser())
                .category(category)
                .description(description)
                .build();
    }
}
