package ru.mail.park.gwent.websocket.handler;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameMessageHandlerContainer implements MessageHandlerContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMessageHandlerContainer.class);
    private final Map<Class<?>, SocketMessageHandler<?>> handlerMap = new HashMap<>();

    @Override
    public void handle(@NotNull WebSocketMessage message, UserProfile forUser) throws HandleException {
        final SocketMessageHandler<?> socketMessageHandler = handlerMap.get(message.getClass());
        if (socketMessageHandler == null) {
            throw new HandleException("no handler for webSocketMessage of " + message.getClass().getName() + " type");
        }
        socketMessageHandler.handleMessage(message, forUser);
        LOGGER.trace("webSocketMessage handled: type =[" + message.getClass().getName() + ']');
    }

    @Override
    public <T extends WebSocketMessage> void registerHandler(@NotNull Class<T> clazz, SocketMessageHandler<T> handler) {
        handlerMap.put(clazz, handler);
    }
}
