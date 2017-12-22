package ru.mail.park.gwent.websocket.message.server;

import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public class ReadyMessage extends WebSocketMessage {
    private String type;

    public ReadyMessage() {
        type = "ReadyMessage";
    }
}
