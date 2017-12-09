package ru.mail.park.gwent.services;

import ru.mail.park.gwent.domains.auth.UserProfile;

import java.util.List;

public interface UserService {
    UserProfile createUser(UserProfile profile);

    void deleteUser(UserProfile profile);

    UserProfile getUserByLogin(String login);

    UserProfile updateUser(UserProfile profile);

    boolean isExist(UserProfile profile);

    List<UserProfile> getUsers(int limit, int offset);
}
