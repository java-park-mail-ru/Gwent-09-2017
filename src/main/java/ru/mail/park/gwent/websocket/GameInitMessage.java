package ru.mail.park.gwent.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;

public class GameInitMessage extends WebSocketMessage {
    private String type;
    private ClientState state;

    public GameInitMessage(ClientState state) {
        this.type = "GameInitState";
        this.state = state;
    }

    @JsonGetter
    public ClientState getState() {
        return state;
    }
}
