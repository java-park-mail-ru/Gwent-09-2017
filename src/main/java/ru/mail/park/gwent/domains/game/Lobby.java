package ru.mail.park.gwent.domains.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lobby {
    private static final Logger LOGGER = LoggerFactory.getLogger(Lobby.class);

    private final int id;
    private final UserPair users;
    private final PlayField playField;

    public Lobby(int id, UserPair users) {
        this.id = id;
        this.users = users;
        playField = new PlayField();
        LOGGER.info("Lobby id=" + id + " created");
    }
}
