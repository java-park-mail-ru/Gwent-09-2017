package ru.mail.park.gwent.services.game;

import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.domains.game.UserPair;
import ru.mail.park.gwent.websocket.handler.HandleException;
import ru.mail.park.gwent.websocket.message.ClientState;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;
import ru.mail.park.gwent.websocket.message.server.ReadyMessage;
import ru.mail.park.gwent.websocket.message.server.WaitingPlayerMessage;

import java.io.IOException;

@Service
public class UserPairService {
    private PlayerQueueService playerQueueService;
    private UserSessionPointService remotePointService;

    public UserPairService(PlayerQueueService playerQueueService,
                           UserSessionPointService remotePointService) {
        this.playerQueueService = playerQueueService;
        this.remotePointService = remotePointService;
    }

    public WebSocketMessage onInitUser(UserProfile user) throws HandleException {
        final UserPair userPair = playerQueueService.joinUser(user);
        if (userPair == null) {
            return new WaitingPlayerMessage();
        }
        return onFindPair(userPair);
    }

    private WebSocketMessage onFindPair(UserPair userPair) {
        try {
            remotePointService.setUserState(userPair.getSecondUser(), ClientState.READY);
            remotePointService.sendMessageToUser(userPair.getSecondUser(), new ReadyMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ReadyMessage();
    }

}
