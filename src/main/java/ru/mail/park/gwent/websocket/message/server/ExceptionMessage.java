package ru.mail.park.gwent.websocket.message.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public class ExceptionMessage extends WebSocketMessage {
    private String type;
    private String reason;

    public ExceptionMessage() {
        this.type = "ExceptionMessage";
    }

    public ExceptionMessage(@JsonProperty("type") String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public ExceptionMessage setReason(String inReason) {
        this.reason = inReason;
        return this;
    }
}
