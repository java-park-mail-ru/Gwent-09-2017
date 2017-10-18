package ru.mail.park.gwent.services;

import ru.mail.park.gwent.domains.UserProfile;

public interface UserService {
    void createUser(UserProfile userProfile);

    @SuppressWarnings("unused")
    void deleteUser(String login);

    UserProfile getUserByLogin(String login);

    void updateUser(String login, UserProfile newProfile);
}
