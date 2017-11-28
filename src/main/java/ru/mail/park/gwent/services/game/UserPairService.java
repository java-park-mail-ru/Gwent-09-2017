package ru.mail.park.gwent.services.game;

import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.game.UserPair;
import ru.mail.park.gwent.domains.game.WebSocketUser;
import ru.mail.park.gwent.websocket.handler.HandleException;
import ru.mail.park.gwent.websocket.message.ClientState;
import ru.mail.park.gwent.websocket.message.GameInitMessage;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

@Service
public class UserPairService {
    private PlayerQueueService playerQueueService;
    private LobbyService lobbyService;

    public UserPairService(PlayerQueueService playerQueueService, LobbyService lobbyService) {
        this.playerQueueService = playerQueueService;
        this.lobbyService = lobbyService;
    }

    public WebSocketMessage onInitUser(WebSocketUser user) throws HandleException {
        final UserPair userPair = playerQueueService.joinUser(user);
        if (userPair == null) {
            return new GameInitMessage(ClientState.WAITING_PLAYER);
        }
        return onFindPair(userPair);
    }

    public WebSocketMessage onFindPair(UserPair userPair) {
        lobbyService.createLobby(userPair);

        return new GameInitMessage(ClientState.READY);
    }

}
