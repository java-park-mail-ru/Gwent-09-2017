package ru.mail.park.gwent.domains.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.websocket.message.ClientState;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

import java.io.IOException;

public class WebSocketUser {
    private WebSocketSession session;
    private UserProfile userProfile;
    private ClientState state;

    public WebSocketUser(WebSocketSession session, UserProfile userProfile) {
        this.session = session;
        this.userProfile = userProfile;
        state = ClientState.INIT;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WebSocketUser
                && ((WebSocketUser) obj).userProfile.equals(userProfile);
    }

    @Override
    public int hashCode() {
        final int primeNumber = 31;
        return session.getId().hashCode() * primeNumber + userProfile.hashCode();
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    public void sendToUser(WebSocketMessage message, ObjectMapper objectMapper) throws IOException {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }

    public ClientState getState() {
        return state;
    }

    public void setState(ClientState state) {
        this.state = state;
    }
}
