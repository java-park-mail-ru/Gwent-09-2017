package ru.mail.park.gwent.services;

import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.UserProfile;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private final Map<String, UserProfile> loginToProfile = new HashMap<>();
    private final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public void addUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    @SuppressWarnings("unused")
    public void deleteUser(String login) {
        loginToProfile.remove(login);
    }

    public void updateUser(String login, UserProfile newProfile) {
        loginToProfile.put(login, newProfile);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    public void updateSession(String sessionId, UserProfile userProfile) {
        addSession(sessionId, userProfile);
    }
}
