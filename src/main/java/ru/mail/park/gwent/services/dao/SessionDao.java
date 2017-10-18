package ru.mail.park.gwent.services.dao;

import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.SessionService;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionDao implements SessionService {
    private final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

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
