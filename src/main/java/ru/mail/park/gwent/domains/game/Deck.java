package ru.mail.park.gwent.domains.game;

import java.util.concurrent.CopyOnWriteArrayList;

public class Deck {
    private CopyOnWriteArrayList<Card> cards;

    public Deck(CopyOnWriteArrayList<Card> cards) {
        this.cards = cards;
    }

    public CopyOnWriteArrayList<Card> getCards() {
        return cards;
    }

    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }
}
