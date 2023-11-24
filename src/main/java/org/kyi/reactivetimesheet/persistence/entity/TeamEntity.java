package org.kyi.reactivetimesheet.persistence.entity;

import lombok.*;
import org.kyi.reactivetimesheet.domain.team.Team;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "teams")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TeamEntity {
    @Id
    String id;
    String name;
    List<UserEntity> members;

    public Team toTeam() {
        return Team.builder()
                .id(id)
                .name(name)
                .members(members.stream().map(UserEntity::toUser).toList())
                .build();
    }
}
