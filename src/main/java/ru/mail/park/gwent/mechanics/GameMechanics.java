package ru.mail.park.gwent.mechanics;

import ru.mail.park.gwent.domains.game.Player;
import ru.mail.park.gwent.websocket.message.client.StepMessage;

public interface GameMechanics {

    void joinPlayer(Player player);

    void startGame();

    void step(Player player, StepMessage message);

    void stepOpponent(Player player, StepMessage message);

    @SuppressWarnings("unused")
    void pass(Player player);

    @SuppressWarnings("unused")
    void passOpponent(Player player);
}
