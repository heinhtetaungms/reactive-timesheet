package org.kyi.reactivetimesheet.domain.user.dto;

import org.kyi.reactivetimesheet.domain.user.User;
import org.kyi.reactivetimesheet.domain.user.type.Role;

public record GetUserDto(String id, String username, String password, Role role, String teamId) {
    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .teamId(teamId)
                .build();
    }
}
