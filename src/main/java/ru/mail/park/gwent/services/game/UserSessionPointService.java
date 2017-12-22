package ru.mail.park.gwent.services.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.domains.game.WebSocketUserState;
import ru.mail.park.gwent.services.UserSessionPoint;
import ru.mail.park.gwent.websocket.message.ClientState;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserSessionPointService implements UserSessionPoint {
    private Map<UserProfile, WebSocketUserState> userStates = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public UserSessionPointService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void registerUser(UserProfile profile, WebSocketSession session) {
        WebSocketUserState userState = new WebSocketUserState(session);
        userStates.put(profile, userState);
    }

    @Override
    public boolean isConnected(UserProfile profile) {
        return userStates.containsKey(profile);
    }

    @Override
    public void removeUser(UserProfile profile) {
        userStates.remove(profile);
    }

    @Override
    public void sendMessageToUser(UserProfile profile, WebSocketMessage message) throws IOException {
        final WebSocketSession session = userStates.get(profile).getSession();
        if (session == null) {
            throw new IOException("No game websocket for user " + profile.getLogin());
        }
        if (!session.isOpen()) {
            throw new IOException("Session is closed or not exists");
        }

        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException ex) {
            throw new IOException("Unable to send message", ex);
        }
    }

    @Override
    public WebSocketSession getSession(UserProfile profile) {
        return userStates.get(profile).getSession();
    }

    @Override
    public ClientState getUserState(UserProfile profile) {
        return userStates.get(profile).getState();
    }

    @Override
    public void setUserState(UserProfile profile, ClientState state) {
        WebSocketUserState userState = userStates.get(profile);
        userState.setState(state);
        userStates.put(profile, userState);
    }
}
