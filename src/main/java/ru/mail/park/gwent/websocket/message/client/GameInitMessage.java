package ru.mail.park.gwent.websocket.message.client;

import com.fasterxml.jackson.annotation.JsonSetter;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public class GameInitMessage extends WebSocketMessage {
    private String type;
    private String deckType;

    @JsonSetter
    public void setDeckType(String deckType) {
        this.deckType = deckType;
    }

    public String getDeckType() {
        return deckType;
    }
}
