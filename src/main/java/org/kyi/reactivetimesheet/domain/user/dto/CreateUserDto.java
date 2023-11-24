package org.kyi.reactivetimesheet.domain.user.dto;

import org.kyi.reactivetimesheet.domain.user.User;
import org.kyi.reactivetimesheet.domain.user.type.Role;

public record CreateUserDto(String username, String password, String passwordConfirmation, Role role, String teamName) {
    public User toUser() {
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
