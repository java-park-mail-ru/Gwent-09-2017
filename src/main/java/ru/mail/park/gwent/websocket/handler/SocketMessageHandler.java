package ru.mail.park.gwent.websocket.handler;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.gwent.domains.game.WebSocketUser;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public abstract class SocketMessageHandler<T extends WebSocketMessage> {
    private final Class<T> clazz;

    public SocketMessageHandler(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    public void handleMessage(@NotNull WebSocketMessage message, @NotNull WebSocketUser user) throws HandleException {
        try {
            handle(clazz.cast(message), user);
        } catch (ClassCastException e) {
            throw new HandleException("Can't read incoming message of type " + message.getClass(), e);
        }
    }

    public abstract void handle(@NotNull T message, @NotNull WebSocketUser user) throws HandleException;
}
