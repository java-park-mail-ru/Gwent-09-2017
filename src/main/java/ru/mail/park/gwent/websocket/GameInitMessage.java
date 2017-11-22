package ru.mail.park.gwent.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameInitMessage extends WebSocketMessage {
    private String type;
    private int state;

    public GameInitMessage() {
        this.type = "GameInitState";
    }

    public GameInitMessage(@JsonProperty("type") String type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
