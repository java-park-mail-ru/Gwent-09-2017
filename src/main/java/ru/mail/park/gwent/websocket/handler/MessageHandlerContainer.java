package ru.mail.park.gwent.websocket.handler;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public interface MessageHandlerContainer {

    void handle(@NotNull WebSocketMessage message, UserProfile forUser) throws HandleException;

    <T extends WebSocketMessage> void registerHandler(@NotNull Class<T> clazz, SocketMessageHandler<T> handler);
}
