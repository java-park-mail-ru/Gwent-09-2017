package ru.mail.park.gwent.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RemotePointService {
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerUser(String login, WebSocketSession webSocketSession) {
        sessions.put(login, webSocketSession);
    }

    @SuppressWarnings("unused")
    public boolean isConnected(String login) {
        return sessions.containsKey(login) && sessions.get(login).isOpen();
    }

    public void removeUser(String login) {
        sessions.remove(login);
    }
}
