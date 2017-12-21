package ru.mail.park.gwent.mechanics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.game.Player;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Service
public class GameSessionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSessionService.class);
    @NotNull
    private final Map<Player, GameSession> usersMap = new HashMap<>();
    @NotNull
    private final Set<GameSession> gameSessions = new LinkedHashSet<>();

    @SuppressWarnings("unused")
    @NotNull
    public Set<GameSession> getGameSessions() {
        return gameSessions;
    }

    @Nullable
    public GameSession getSessionForUser(@NotNull Player player) {
        return usersMap.get(player);
    }

    public boolean isPlaying(@NotNull Player player) {
        return usersMap.containsKey(player);
    }

    public void startGame(@NotNull Player first, @NotNull Player second) {
        final GameSession gameSession = new GameSession(first, second);
        gameSessions.add(gameSession);
        usersMap.put(gameSession.getFirst(), gameSession);
        usersMap.put(gameSession.getSecond(), gameSession);
        LOGGER.info("Game session " + gameSession.getSessionId() + " started. " + gameSession.toString());
    }
}
