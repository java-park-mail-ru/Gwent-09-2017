package ru.mail.park.gwent.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WantPlayMessage extends WebSocketMessage {
    private String type;
    private int state;

    public WantPlayMessage(@JsonProperty("type") String type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
