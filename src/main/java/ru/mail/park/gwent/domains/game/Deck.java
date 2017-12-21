package ru.mail.park.gwent.domains.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> giveOutCards(int numberOfCard) {
        if (numberOfCard > cards.size()) {
            return Collections.emptyList();
        }

        final List<Card> result = new ArrayList<>();

        for (int i = 0; i < numberOfCard; i++) {
            final Card card = cards.remove(i);
            result.add(card);
        }

        return result;
    }

    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }
}
