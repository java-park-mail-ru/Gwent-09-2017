package ru.mail.park.gwent.websocket;

public interface MessageHandlerContainer {
    void handle(Message message, String forProfile) throws HandleException;

    <T extends Message> void registerHandler(Class<T> clazz, MessageHandler<T> handler);
}
