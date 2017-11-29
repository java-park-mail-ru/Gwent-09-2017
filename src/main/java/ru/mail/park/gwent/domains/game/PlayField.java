package ru.mail.park.gwent.domains.game;

import ru.mail.park.gwent.services.game.Mechanics;

public class PlayField {
    private Deck firstPlayerHand;
    private Deck secondPlayerHand;

    private PlayerLines firstPlayerLines;
    private PlayerLines secondPlayerLines;

    public PlayField() {
        final Deck deck = Mechanics.genarateDeck();
        firstPlayerHand = Mechanics.getHand(deck);
        secondPlayerHand = Mechanics.getHand(deck);

        firstPlayerLines = new PlayerLines();
        secondPlayerLines = new PlayerLines();
    }

    public Deck getFirstPlayerHand() {
        return firstPlayerHand;
    }

    public Deck getSecondPlayerHand() {
        return secondPlayerHand;
    }

    public PlayerLines getFirstPlayerLines() {
        return firstPlayerLines;
    }

    public PlayerLines getSecondPlayerLines() {
        return secondPlayerLines;
    }
}
