package ru.mail.park.gwent.websocket.handler;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.domains.game.Player;
import ru.mail.park.gwent.mechanics.GameMechanics;
import ru.mail.park.gwent.websocket.message.client.JoinGameMessage;

import javax.annotation.PostConstruct;

@Component
public class JoinGameHandler extends SocketMessageHandler<JoinGameMessage> {
    @NotNull
    private GameMechanics gameMechanics;
    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    JoinGameHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(JoinGameMessage.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }


    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGameMessage.class, this);
    }

    @Override
    public void handle(@NotNull JoinGameMessage message, @NotNull UserProfile user) {
        gameMechanics.joinPlayer(new Player(user));
    }
}
