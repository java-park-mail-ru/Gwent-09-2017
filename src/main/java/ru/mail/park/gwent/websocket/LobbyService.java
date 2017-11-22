package ru.mail.park.gwent.websocket;

import org.springframework.stereotype.Service;

@Service
public class LobbyService {
    private PlayerQueue playerQueue;

    public LobbyService(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
    }

    public WebSocketMessage onInitUser(WebSocketUser user) throws HandleException {
        final Lobby users = playerQueue.addUser(user);
        if (users == null) {
            final GameInitMessage state = new GameInitMessage();
            state.setState(ClientState.WAITING_PLAYER.getId());
            return state;
        }
        return onFindLobby(users);
    }

    public WebSocketMessage onFindLobby(Lobby userPair) throws HandleException {
        throw new HandleException("Not implemented yet");
    }

}
