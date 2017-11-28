package ru.mail.park.gwent.websocket;

import org.springframework.stereotype.Service;

@Service
public class UserPairService {
    private PlayerQueue playerQueue;
    private LobbyService lobbyService;

    public UserPairService(PlayerQueue playerQueue, LobbyService lobbyService) {
        this.playerQueue = playerQueue;
        this.lobbyService = lobbyService;
    }

    public WebSocketMessage onInitUser(WebSocketUser user) throws HandleException {
        final UserPair userPair = playerQueue.addUser(user);
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
