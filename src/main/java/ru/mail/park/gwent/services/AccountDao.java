package ru.mail.park.gwent.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.gwent.domains.UserProfile;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AccountDao implements AccountService {
    private final Map<String, UserProfile> loginToProfile = new HashMap<>();
    private final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

    @Override
    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    @Override
    public void addUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    @Override
    public void deleteUser(String login) {
        loginToProfile.remove(login);
    }

    @Override
    public void updateUser(String login, UserProfile newProfile) {
        loginToProfile.put(login, newProfile);
    }

    @Override
    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    @Override
    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    @Override
    public void updateSession(String sessionId, UserProfile userProfile) {
        addSession(sessionId, userProfile);
    }
}
