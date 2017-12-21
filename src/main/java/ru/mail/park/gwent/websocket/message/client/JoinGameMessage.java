package ru.mail.park.gwent.websocket.message.client;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public class JoinGameMessage extends WebSocketMessage {
    private String deckType;

    @JsonGetter
    public String getDeckType() {
        return deckType;
    }

    @JsonSetter
    public void setDeckType(String deckType) {
        this.deckType = deckType;
    }
}
