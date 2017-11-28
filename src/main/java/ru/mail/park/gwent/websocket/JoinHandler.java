package ru.mail.park.gwent.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JoinHandler extends SocketMessageHandler<WantPlayMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JoinHandler.class);

    private UserPairService userPairService;
    private ObjectMapper objectMapper;

    public JoinHandler(UserPairService userPairService, ObjectMapper objectMapper) {
        super(WantPlayMessage.class);
        this.userPairService = userPairService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(@NotNull WantPlayMessage message, @NotNull WebSocketUser forUser) throws HandleException {
        if (message.getState() == ClientState.INIT) {
            final WebSocketMessage outputMsg = userPairService.onInitUser(forUser);
            if (outputMsg != null) {
                try {
                    forUser.sendToUser(outputMsg, objectMapper);
                } catch (IOException e) {
                    LOGGER.error("Error while send message", e);
                }
            }
        }
    }
}
