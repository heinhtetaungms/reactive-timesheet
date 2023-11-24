package org.kyi.reactivetimesheet.persistence.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kyi.reactivetimesheet.domain.team.TeamUtils;
import org.kyi.reactivetimesheet.domain.team.repository.TeamRepository;
import org.kyi.reactivetimesheet.domain.user.User;
import org.kyi.reactivetimesheet.domain.user.UserUtils;
import org.kyi.reactivetimesheet.domain.user.dto.CreateUserDto;
import org.kyi.reactivetimesheet.domain.user.dto.GetUserDto;
import org.kyi.reactivetimesheet.domain.user.repository.UserRepository;
import org.kyi.reactivetimesheet.persistence.exception.UserServiceException;
import org.kyi.reactivetimesheet.persistence.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<GetUserDto> findById(String userId) {
        return userRepository.findById(userId)
                .map(User::toGetUserDto)
                .switchIfEmpty(Mono.error(new UserServiceException("cannot find user by id : " + userId)));
    }

    @Override
    public Mono<GetUserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::toGetUserDto)
                .switchIfEmpty(Mono.error(new UserServiceException("cannot find user by username : " + username)));
    }

    @Override
    public Mono<GetUserDto> addUser(Mono<CreateUserDto> createUserDtoMono) {
        return createUserDtoMono
                .flatMap(createUserDto -> userRepository
                        .findByUsername(createUserDto.username())
                        .hasElement()
                        .flatMap(isUserPresent ->
                                isUserPresent
                                ?
                                Mono.error(new UserServiceException("user with username " + createUserDto.username() + " already exists."))
                                :
                                createUser(createUserDto)));
    }

    @Override
    public Mono<GetUserDto> createUser(CreateUserDto createUserDto) {
        return userRepository
                .save(createUserDto.toUser())
                .map(User::toGetUserDto);
    }

    @Override
    public Mono<GetUserDto> deleteUser(String userId) {
        return userRepository
                .findById(userId)
                .flatMap(user -> userRepository.delete(userId)
                        .then(deleteMemberFromTeam(user)))
                .switchIfEmpty(Mono.error(new UserServiceException("cannot find user to delete")));
    }

    @Override
    public Mono<GetUserDto> deleteMemberFromTeam(User member) {
        var teamId = UserUtils.toTeamId.apply(member);
        if (teamId != null) {
            return teamRepository
                    .findById(teamId)
                    .flatMap(team -> {
                        TeamUtils.toMembers.apply(team)
                                .remove(member);
                        return teamRepository.save(team)
                                .flatMap(t -> Mono.just(member.toGetUserDto()));
                    })
                    .switchIfEmpty(Mono.error(new UserServiceException("cannot find user's team by username " + member.getUsername())));
        }
        return Mono.just(member.toGetUserDto());
    }
}
