package ru.mail.park.gwent.domains.game;

import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean hasCard(Card card) {
        return cards.contains(card);
    }
}
