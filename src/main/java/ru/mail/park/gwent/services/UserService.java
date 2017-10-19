package ru.mail.park.gwent.services;

import ru.mail.park.gwent.domains.UserProfile;

public interface UserService {
    UserProfile createUser(UserProfile profile);

    @SuppressWarnings("unused")
    void deleteUser(UserProfile profile);

    UserProfile getUserByLogin(String login);

    UserProfile updateUser(UserProfile profile);
}
