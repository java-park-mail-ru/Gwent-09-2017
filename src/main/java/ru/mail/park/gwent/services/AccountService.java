package ru.mail.park.gwent.services;

import ru.mail.park.gwent.domains.UserProfile;

public interface AccountService {
    UserProfile getUserByLogin(String login);

    void addUser(UserProfile userProfile);

    @SuppressWarnings("unused")
    void deleteUser(String login);

    void updateUser(String login, UserProfile newProfile);

    UserProfile getUserBySessionId(String sessionId);

    void addSession(String sessionId, UserProfile userProfile);

    void deleteSession(String sessionId);

    void updateSession(String sessionId, UserProfile userProfile);
}
