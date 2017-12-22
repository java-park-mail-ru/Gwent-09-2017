package ru.mail.park.gwent.websocket.message.server;

import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public class WaitingPlayerMessage extends WebSocketMessage {
    private String type;

    public WaitingPlayerMessage() {
        this.type = "WaitingPlayerMessage";
    }
}
