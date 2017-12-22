package ru.mail.park.gwent.websocket.message.server;

import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public class ExceptionMessage extends WebSocketMessage {
    private String reason;

    public String getReason() {
        return reason;
    }

    public ExceptionMessage setReason(String inReason) {
        this.reason = inReason;
        return this;
    }
}
