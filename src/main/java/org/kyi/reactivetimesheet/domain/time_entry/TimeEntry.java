package org.kyi.reactivetimesheet.domain.time_entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.kyi.reactivetimesheet.domain.time_entry.dto.GetTimeEntryDto;
import org.kyi.reactivetimesheet.domain.time_entry.type.Category;
import org.kyi.reactivetimesheet.domain.user.User;
import org.kyi.reactivetimesheet.persistence.entity.TimeEntryEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class TimeEntry {
    String id;
    LocalDateTime timeFrom;
    LocalDateTime timeTo;
    User user;
    Category category;
    String description;

    public TimeEntryEntity toEntity() {
        return TimeEntryEntity.builder()
                .id(id)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .user(user.toEntity())
                .category(category)
                .description(description)
                .build();
    }

    public GetTimeEntryDto toGetTimeEntryDto() {
        return new GetTimeEntryDto(
                id,
                timeFrom,
                timeTo,
                user.toGetUserDto(),
                category,
                description);
    }
}
