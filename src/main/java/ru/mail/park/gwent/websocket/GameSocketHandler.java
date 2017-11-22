package ru.mail.park.gwent.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.UserService;

import java.io.IOException;

import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;
import static ru.mail.park.gwent.consts.Constants.SESSION_USER_PROFILE_KEY;

public class GameSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSocketHandler.class);
    private static final CloseStatus ACCESS_DENIED = new CloseStatus(4500, "Not logged in. Access denied");

    private SocketMessageHandlerManager handlerManager;
    private UserService userService;
    private ObjectMapper objectMapper;

    public GameSocketHandler(@NotNull SocketMessageHandlerManager handlerManager,
                             @NotNull UserService userService,
                             ObjectMapper objectMapper) {
        this.handlerManager = handlerManager;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        final UserProfile profile = (UserProfile) session.getAttributes().get(SESSION_USER_PROFILE_KEY);
        if (profile == null || !userService.isExist(profile)) {
            LOGGER.warn("User requested websocket is not registred or not logged in. Openning websocket session is denied.");
            closeSessionSilently(session, ACCESS_DENIED);
        }
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws IOException {
        if (!session.isOpen()) {
            return;
        }
        final UserProfile profile = (UserProfile) session.getAttributes().get(SESSION_USER_PROFILE_KEY);
        if (profile == null || !userService.isExist(profile)) {
            LOGGER.warn("User requested websocket is not registred or not logged in. Openning websocket session is denied.");
            closeSessionSilently(session, ACCESS_DENIED);
            return;
        }
        try {
            handleMessage(new WebSocketUser(session, profile), message);
        } catch (HandleException e) {
            session.sendMessage(new TextMessage(exceptionToJson(e)));
        }
    }

    private void closeSessionSilently(@NotNull WebSocketSession session, @Nullable CloseStatus closeStatus) {
        CloseStatus status = closeStatus;
        if (status == null) {
            status = SERVER_ERROR;
        }

        try {
            session.close(status);
        } catch (IOException e) {
            LOGGER.error("Can't close WebSocketSession", e);
        }
    }

    private String exceptionToJson(Throwable ex) {
        try {
            final ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
            if (responseStatus != null) {
                String reason = ex.getMessage();
                if (reason == null) {
                    reason = responseStatus.reason();
                }
                return objectMapper.writeValueAsString(new ExceptionMessage().setReason(reason));
            }
            return objectMapper.writeValueAsString(new ExceptionMessage().setReason(ex.getMessage()));
        } catch (JsonProcessingException e) {
            return "Error in processing Exception";
        }
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    private void handleMessage(WebSocketUser user, @NotNull TextMessage text) throws HandleException {
        final WebSocketMessage message;
        try {
            message = objectMapper.readValue(text.getPayload(), WebSocketMessage.class);
        } catch (IOException e) {
            LOGGER.error("Wrong json format at game response", e);
            LOGGER.error(text.getPayload());
            return;
        }
        try {
            handlerManager.handle(message, user);
        } catch (HandleException e) {
            LOGGER.error("Can't handle webSocketMessage of type " + message.getClass().getName() + " with content: " + text, e);
            throw e;
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        LOGGER.warn("Websocket transport problem", exception);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, CloseStatus status) {
        final UserProfile profile = (UserProfile) session.getAttributes().get(SESSION_USER_PROFILE_KEY);
        if (profile == null) {
            LOGGER.warn("User disconnected but his session was not found (closeStatus=" + status + ')');
        }
    }
}
