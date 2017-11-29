package ru.mail.park.gwent.services.game;

import ru.mail.park.gwent.domains.game.Card;
import ru.mail.park.gwent.domains.game.CardType;
import ru.mail.park.gwent.domains.game.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mechanics {
    private static final int NUMBER_OF_CARDS_ONE_TYPE = 8;
    private static final int NUMBER_OF_CARDS_IN_HAND = 10;

    public static Deck genarateDeck() {
        final List<Card> cards = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CARDS_ONE_TYPE; i++) {
            final Card card = new Card(i + 1, CardType.FRONT);
            cards.add(card);
        }
        for (int i = 0; i < NUMBER_OF_CARDS_ONE_TYPE; i++) {
            final Card card = new Card(i + 1, CardType.MIDDLE);
            cards.add(card);
        }
        for (int i = 0; i < NUMBER_OF_CARDS_ONE_TYPE; i++) {
            final Card card = new Card(i + 1, CardType.BACK);
            cards.add(card);
        }

        return new Deck(cards);
    }

    public static Deck getHand(Deck deck) {
        Collections.shuffle(deck.getCards());

        final List<Card> deckCards = deck.getCards();
        final List<Card> handCards = deckCards.subList(1, NUMBER_OF_CARDS_IN_HAND);

        return new Deck(handCards);
    }


}
