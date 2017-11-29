package ru.mail.park.gwent.domains.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.park.gwent.services.game.Mechanics;

public class Lobby {
    private static final Logger LOGGER = LoggerFactory.getLogger(Lobby.class);

    private final int id;
    private final UserPair users;
    private final Mechanics mechanics;

    public Lobby(int id, UserPair users) {
        this.id = id;
        this.users = users;
        mechanics = new Mechanics();
        LOGGER.info("Lobby id=" + id + " created");
    }
}
