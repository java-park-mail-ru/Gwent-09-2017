package ru.mail.park.gwent.services;

import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.domains.game.Player;

import java.util.List;

public interface UserService {
    UserProfile createUser(UserProfile profile);

    void deleteUser(UserProfile profile);

    UserProfile getUserProfile(String login);

    UserProfile updateUser(UserProfile profile);

    boolean isExist(UserProfile profile);

    Player getPlayerInfo(String login);

    List<Player> getPlayers(int limit, int offset);
}
