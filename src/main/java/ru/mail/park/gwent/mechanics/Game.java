package ru.mail.park.gwent.mechanics;

import ru.mail.park.gwent.domains.game.Player;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public interface Game {

    void joinPlayer(Player player);

    void step(Player player, WebSocketMessage message);

    void opponentStep(Player player, WebSocketMessage message);

    void pass(Player player);

    void opponentPass(Player player);
}
