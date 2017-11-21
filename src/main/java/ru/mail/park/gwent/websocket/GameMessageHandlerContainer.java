package ru.mail.park.gwent.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameMessageHandlerContainer implements MessageHandlerContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMessageHandlerContainer.class);
    private final Map<Class<?>, MessageHandler<?>> handlerMap = new HashMap<>();

    @Override
    public void handle(Message message, String forProfile) throws HandleException {
        final MessageHandler<?> messageHandler = handlerMap.get(message.getClass());
        if (messageHandler == null) {
            throw new HandleException("no handler for message of " + message.getClass().getName() + " type");
        }
        messageHandler.handleMessage(message, forProfile);
        LOGGER.trace("message handled: type =[" + message.getClass().getName() + ']');
    }

    @Override
    public <T extends Message> void registerHandler(Class<T> clazz, MessageHandler<T> handler) {
        handlerMap.put(clazz, handler);
    }
}
