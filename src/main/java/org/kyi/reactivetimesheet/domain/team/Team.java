package org.kyi.reactivetimesheet.domain.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.kyi.reactivetimesheet.domain.team.dto.GetTeamDto;
import org.kyi.reactivetimesheet.domain.user.User;
import org.kyi.reactivetimesheet.persistence.entity.TeamEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Team {
    String id;
    String name;
    List<User> members;

    public Team withMembers(List<User> newMembers) {
        return Team.builder()
                .id(id)
                .name(name)
                .members(members)
                .build();
    }
    public TeamEntity toEntity() {
        return TeamEntity.builder()
                .id(id)
                .name(name)
                .members(members.stream().map(User::toEntity).toList())
                .build();
    }
    public GetTeamDto toGetTeamDto() {
        return new GetTeamDto(
                id,
                name,
                members.stream().map(User::toGetUserDto).toList()
        );
    }
}
