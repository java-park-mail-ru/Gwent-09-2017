package ru.mail.park.gwent.mechanics;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.gwent.domains.game.PlayField;
import ru.mail.park.gwent.domains.game.Player;

import java.util.concurrent.atomic.AtomicLong;

public class GameSession {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    @NotNull
    private final Long sessionId;
    @NotNull
    private final Player first;
    @NotNull
    private final Player second;
    @NotNull
    private final PlayField playField;

    public GameSession(@NotNull Player first,
                       @NotNull Player second) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.first = first;
        this.second = second;
        this.playField = new PlayField();
    }

    @NotNull
    public Long getSessionId() {
        return sessionId;
    }

    @NotNull
    public Player getFirst() {
        return first;
    }

    @NotNull
    public Player getSecond() {
        return second;
    }

    @NotNull
    public PlayField getPlayField() {
        return playField;
    }
}
