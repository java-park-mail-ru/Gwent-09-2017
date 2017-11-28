package ru.mail.park.gwent.websocket;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LobbyService {
    private final AtomicInteger roomId = new AtomicInteger();

    public Lobby createLobby(UserPair users) {
        return new Lobby(roomId.getAndIncrement(), users);
    }
}
