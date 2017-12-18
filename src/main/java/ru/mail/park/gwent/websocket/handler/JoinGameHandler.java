package ru.mail.park.gwent.websocket.handler;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.services.game.UserPairService;
import ru.mail.park.gwent.services.game.UserSessionPointService;
import ru.mail.park.gwent.websocket.message.ClientState;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;
import ru.mail.park.gwent.websocket.message.client.JoinGameMessage;
import ru.mail.park.gwent.websocket.message.server.ReadyMessage;
import ru.mail.park.gwent.websocket.message.server.WaitingPlayerMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class JoinGameHandler extends SocketMessageHandler<JoinGameMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JoinGameHandler.class);

    private MessageHandlerContainer messageHandlerContainer;
    private UserSessionPointService remotePointService;
    private UserPairService userPairService;

    public JoinGameHandler(MessageHandlerContainer messageHandlerContainer,
                           UserSessionPointService remotePointService,
                           UserPairService userPairService) {
        super(JoinGameMessage.class);
        this.messageHandlerContainer = messageHandlerContainer;
        this.remotePointService = remotePointService;
        this.userPairService = userPairService;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGameMessage.class, this);
    }

    @Override
    public void handle(@NotNull JoinGameMessage message, @NotNull UserProfile user) throws HandleException {
        ClientState state = remotePointService.getUserState(user);
        if (state == ClientState.INIT) {
            final WebSocketMessage outputMsg = userPairService.onInitUser(user);

            if (outputMsg instanceof WaitingPlayerMessage) {
                remotePointService.setUserState(user, ClientState.WAITING_PLAYER);
            } else if (outputMsg instanceof ReadyMessage) {
                remotePointService.setUserState(user, ClientState.READY);
            }

            try {
                remotePointService.sendMessageToUser(user, outputMsg);
            } catch (IOException e) {
                LOGGER.error("Error while send message", e);
            }
        } else {
            throw new HandleException("Wrong client state");
        }
    }
}
