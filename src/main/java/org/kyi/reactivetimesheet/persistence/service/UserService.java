package org.kyi.reactivetimesheet.persistence.service;

import org.kyi.reactivetimesheet.domain.user.User;
import org.kyi.reactivetimesheet.domain.user.dto.CreateUserDto;
import org.kyi.reactivetimesheet.domain.user.dto.GetUserDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<GetUserDto> findById(String userId);
    Mono<GetUserDto> findByUsername(String username);
    Mono<GetUserDto> addUser(Mono<CreateUserDto> createUserDtoMono);
    Mono<GetUserDto> createUser(CreateUserDto createUserDto);
    Mono<GetUserDto> deleteUser(String userId);
    Mono<GetUserDto> deleteMemberFromTeam(User member);
}
