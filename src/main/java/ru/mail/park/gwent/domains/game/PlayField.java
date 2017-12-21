package ru.mail.park.gwent.domains.game;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.gwent.services.game.Mechanics;

public class PlayField {
    @NotNull
    private Deck firstPlayerHand;
    @NotNull
    private Deck secondPlayerHand;

    @NotNull
    private PlayerLines firstPlayerLines;
    @NotNull
    private PlayerLines secondPlayerLines;

    public PlayField() {
        final Deck deck = Mechanics.genarateDeck();
        firstPlayerHand = Mechanics.getHand(deck);
        secondPlayerHand = Mechanics.getHand(deck);

        firstPlayerLines = new PlayerLines();
        secondPlayerLines = new PlayerLines();
    }

    @NotNull
    public Deck getFirstPlayerHand() {
        return firstPlayerHand;
    }

    @NotNull
    public Deck getSecondPlayerHand() {
        return secondPlayerHand;
    }

    @NotNull
    public PlayerLines getFirstPlayerLines() {
        return firstPlayerLines;
    }

    @NotNull
    public PlayerLines getSecondPlayerLines() {
        return secondPlayerLines;
    }
}
