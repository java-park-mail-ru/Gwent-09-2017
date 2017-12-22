package ru.mail.park.gwent.websocket.message.client;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import ru.mail.park.gwent.domains.game.Card;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;

public class StepMessage extends WebSocketMessage {
    private Card card;

    public StepMessage(Card card) {
        this.card = card;
    }

    @JsonGetter
    public Card getCard() {
        return card;
    }

    @JsonSetter
    public void setCard(Card card) {
        this.card = card;
    }
}
