package ru.mail.park.gwent.services.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.game.UserPair;
import ru.mail.park.gwent.domains.game.WebSocketUser;
import ru.mail.park.gwent.websocket.handler.HandleException;
import ru.mail.park.gwent.websocket.message.server.ReadyMessage;
import ru.mail.park.gwent.websocket.message.server.WaitingPlayerMessage;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

import java.io.IOException;

@Service
public class UserPairService {
    private PlayerQueueService playerQueueService;
    private ObjectMapper objectMapper;
//    private LobbyService lobbyService;

    public UserPairService(PlayerQueueService playerQueueService, LobbyService lobbyService, ObjectMapper objectMapper) {
        this.playerQueueService = playerQueueService;
//        this.lobbyService = lobbyService;
        this.objectMapper = objectMapper;
    }

    public WebSocketMessage onInitUser(WebSocketUser user) throws HandleException {
        final UserPair userPair = playerQueueService.joinUser(user);
        if (userPair == null) {
            return new WaitingPlayerMessage();
        }
        return onFindPair(userPair);
    }

    public WebSocketMessage onFindPair(UserPair userPair) {
//        lobbyService.createLobby(userPair);
        try {
            userPair.getSecondUser().sendToUser(new ReadyMessage(), objectMapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ReadyMessage();
    }

}
