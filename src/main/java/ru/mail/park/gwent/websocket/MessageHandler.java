package ru.mail.park.gwent.websocket;

import org.jetbrains.annotations.NotNull;

public abstract class MessageHandler<T extends Message> {
    private final Class<T> clazz;

    public MessageHandler(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    public void handleMessage(Message message, String forProfile) throws HandleException {
        try {
            handle(clazz.cast(message), forProfile);
        } catch (ClassCastException ex) {
            throw new HandleException("Can't read incoming message of type " + message.getClass(), ex);
        }
    }

    public abstract void handle(T message, String forProfile) throws HandleException;

}
