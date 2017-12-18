package ru.mail.park.gwent.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.mail.park.gwent.consts.Constants;
import ru.mail.park.gwent.domains.game.WebSocketUser;
import ru.mail.park.gwent.services.game.UserPairService;
import ru.mail.park.gwent.websocket.message.ClientState;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;
import ru.mail.park.gwent.websocket.message.client.GameInitMessage;
import ru.mail.park.gwent.websocket.message.server.ReadyMessage;
import ru.mail.park.gwent.websocket.message.server.WaitingPlayerMessage;

import java.io.IOException;

@Component
public class JoinHandler extends SocketMessageHandler<GameInitMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JoinHandler.class);

    private UserPairService userPairService;
    private ObjectMapper objectMapper;

    public JoinHandler(UserPairService userPairService, ObjectMapper objectMapper) {
        super(GameInitMessage.class);
        this.userPairService = userPairService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(@NotNull GameInitMessage message, @NotNull WebSocketUser forUser) throws HandleException {
        if (forUser.getState() == ClientState.INIT) {
            final WebSocketMessage outputMsg = userPairService.onInitUser(forUser);

            if (outputMsg instanceof WaitingPlayerMessage) {
                forUser.setState(ClientState.WAITING_PLAYER);
            } else if (outputMsg instanceof ReadyMessage) {
                forUser.setState(ClientState.READY);
            }
            forUser.getSession().getAttributes().put(Constants.SESSION_WEB_SOCKET_USER_KEY, forUser);

            try {
                forUser.sendToUser(outputMsg, objectMapper);
            } catch (IOException e) {
                LOGGER.error("Error while send message", e);
            }
        } else {
            throw new HandleException("Wrong client state");
        }
    }
}
