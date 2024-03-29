package org.kyi.reactivetimesheet.domain.team;

import org.kyi.reactivetimesheet.domain.user.User;

import java.util.List;
import java.util.function.Function;

public interface TeamUtils {
    Function<Team, String> toId = team -> team.id;
    Function<Team, List<User>> toMembers = team -> team.members;
}
