package org.kyi.reactivetimesheet.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.kyi.reactivetimesheet.domain.user.User;
import org.kyi.reactivetimesheet.domain.user.type.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    String id;
    String username;
    String password;
    Role role;
    String teamId;

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
