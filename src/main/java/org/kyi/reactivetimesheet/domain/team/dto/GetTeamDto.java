package org.kyi.reactivetimesheet.domain.team.dto;

import org.kyi.reactivetimesheet.domain.team.Team;
import org.kyi.reactivetimesheet.domain.user.dto.GetUserDto;

import java.util.List;

public record GetTeamDto(String id, String name, List<GetUserDto> members) {
    public Team toTeam() {
        return Team.builder()
                .id(id)
                .name(name)
                .members(members.stream().map(GetUserDto::toUser).toList())
                .build();
    }
}
