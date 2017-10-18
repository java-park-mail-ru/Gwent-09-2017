package ru.mail.park.gwent.services;

import ru.mail.park.gwent.domains.UserProfile;

public interface SessionService {
    void addSession(String sessionId, UserProfile userProfile);

    void deleteSession(String sessionId);

    UserProfile getUserBySessionId(String sessionId);

    void updateSession(String sessionId, UserProfile userProfile);
}
