package ru.mail.park.gwent.services;

import org.springframework.web.socket.WebSocketSession;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.websocket.message.ClientState;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

import java.io.IOException;

public interface UserSessionPoint {
    void registerUser(UserProfile profile, WebSocketSession session);

    boolean isConnected(UserProfile profile);

    void removeUser(UserProfile profile);

    void sendMessageToUser(UserProfile profile, WebSocketMessage message) throws IOException;

    WebSocketSession getSession(UserProfile profile);

    ClientState getUserState(UserProfile profile);

    void setUserState(UserProfile profile, ClientState state);
}
