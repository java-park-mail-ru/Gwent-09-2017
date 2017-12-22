package ru.mail.park.gwent.mechanics;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.game.Card;
import ru.mail.park.gwent.domains.game.PlayField;
import ru.mail.park.gwent.domains.game.Player;
import ru.mail.park.gwent.websocket.message.client.StepMessage;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class GameMechanicsImpl implements GameMechanics {
    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanicsImpl.class);

    @NotNull
    private final GameSessionService gameSessionService;

    @NotNull
    private ConcurrentLinkedQueue<Player> waiters = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(@NotNull GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @Override
    public void joinPlayer(Player player) {
        if (gameSessionService.isPlaying(player)) {
            return;
        }
        waiters.add(player);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("User %s added to the waiting list", player.getLogin()));
        }
    }

    @Override
    public void startGame() {
        final Set<Player> matchedPlayers = new LinkedHashSet<>();

        while (waiters.size() >= 2 || waiters.size() >= 1 && matchedPlayers.size() >= 1) {
            final Player candidate = waiters.poll();
            matchedPlayers.add(candidate);
            if (matchedPlayers.size() == 2) {
                final Iterator<Player> iterator = matchedPlayers.iterator();
                gameSessionService.startGame(iterator.next(), iterator.next());
                matchedPlayers.clear();
            }
        }
        waiters.addAll(matchedPlayers);
    }

    @Override
    public void step(Player player, StepMessage message) {
        GameSession gameSession = gameSessionService.getSessionForUser(player);
        if (gameSession != null) {
            final PlayField playField = gameSession.getPlayField();
            Card card = message.getCard();
            playField.getFirstPlayerHand().removeCard(card);
            playField.getFirstPlayerLines().addCartToLine(card);
        }
    }

    @Override
    public void stepOpponent(Player player, StepMessage message) {
        GameSession gameSession = gameSessionService.getSessionForUser(player);
        if (gameSession != null) {
            final PlayField playField = gameSession.getPlayField();
            Card card = message.getCard();
            playField.getSecondPlayerHand().removeCard(card);
            playField.getSecondPlayerLines().addCartToLine(card);
        }
    }

    @Override
    public void pass(Player player) {

    }

    @Override
    public void passOpponent(Player player) {

    }
}
