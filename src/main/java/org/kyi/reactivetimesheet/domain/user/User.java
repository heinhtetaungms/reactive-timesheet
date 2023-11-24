package org.kyi.reactivetimesheet.domain.user;

import lombok.*;
import org.kyi.reactivetimesheet.domain.user.dto.GetUserDto;
import org.kyi.reactivetimesheet.domain.user.type.Role;
import org.kyi.reactivetimesheet.persistence.entity.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class User {
    String id;
    String username;
    String password;
    Role role;
    String teamId;

    public User withTeamId(String newTeamId) {
        return User.builder()
                .id(id)
                .username(username)
                .role(role)
                .teamId(newTeamId)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .teamId(teamId)
                .build();
    }

    public GetUserDto toGetUserDto() {
        return new GetUserDto(
                id,
                username,
                password,
                role,
                teamId
        );
    }

}
