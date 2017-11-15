package ru.mail.park.gwent.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.UserService;

import java.io.IOException;

import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;
import static ru.mail.park.gwent.controllers.SessionController.SESSION_KEY;

public class GameSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSocketHandler.class);
    private static final CloseStatus ACCESS_DENIED = new CloseStatus(4500, "Not logged in. Access denied");

    private UserService userService;
    private final RemotePointService remotePointService;
    private final ObjectMapper objectMapper;
    private final MessageHandlerContainer messageHandlerContainer;

    public GameSocketHandler(UserService userService,
                             RemotePointService remotePointService,
                             ObjectMapper objectMapper, MessageHandlerContainer messageHandlerContainer) {
        this.userService = userService;
        this.remotePointService = remotePointService;
        this.objectMapper = objectMapper;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        final UserProfile profile = (UserProfile) session.getAttributes().get(SESSION_KEY);
        if (profile == null || userService.getUserByLogin(profile.getLogin()) == null) {
            LOGGER.warn("User requested websocket is not registred or not logged in. Openning websocket session is denied.");
            closeSessionSilently(session, ACCESS_DENIED);
            return;
        }
        final String login = profile.getLogin();
        remotePointService.registerUser(login, session);
    }

    private void closeSessionSilently(WebSocketSession session, CloseStatus closeStatus) {
        final CloseStatus status;
        if (closeStatus == null) {
            status = SERVER_ERROR;
        } else {
            status = closeStatus;
        }
        try {
            session.close(status);
        } catch (IOException e) {
            LOGGER.error("Can't close WebSocketSession");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (!session.isOpen()) {
            return;
        }
        final UserProfile profile = (UserProfile) session.getAttributes().get(SESSION_KEY);
        if (profile == null || userService.getUserByLogin(profile.getLogin()) == null) {
            closeSessionSilently(session, ACCESS_DENIED);
            return;
        }
        handleMessage(profile, message);
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    private void handleMessage(UserProfile profile, TextMessage text) {
        final Message message;
        try {
            message = objectMapper.readValue(text.getPayload(), Message.class);
        } catch (IOException ex) {
            LOGGER.error("Wrong json format at game response", ex);
            return;
        }
        try {
            messageHandlerContainer.handle(message, profile.getLogin());
        } catch (HandleException ex) {
            LOGGER.error("Can't handle message of type " + message.getClass().getName() + " with content: " + text, ex);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.warn("Websocket transport problem", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        final UserProfile profile = (UserProfile) session.getAttributes().get(SESSION_KEY);
        if (profile == null) {
            LOGGER.warn("User disconnected but his session was not found (closeStatus=" + status + ')');
            return;
        }
        remotePointService.removeUser(profile.getLogin());
    }
}
