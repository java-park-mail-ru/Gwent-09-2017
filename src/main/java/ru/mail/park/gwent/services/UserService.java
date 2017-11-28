package ru.mail.park.gwent.services;

import ru.mail.park.gwent.domains.auth.UserProfile;

public interface UserService {
    UserProfile createUser(UserProfile profile);

    void deleteUser(UserProfile profile);

    UserProfile getUserByLogin(String login);

    UserProfile updateUser(UserProfile profile);

    boolean isExist(UserProfile profile);
}
