package ru.mail.park.gwent.services.game;

import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.game.Lobby;
import ru.mail.park.gwent.domains.game.UserPair;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LobbyService {
    private final AtomicInteger roomId = new AtomicInteger();

    public Lobby createLobby(UserPair users) {
        return new Lobby(roomId.getAndIncrement(), users);
    }
}
