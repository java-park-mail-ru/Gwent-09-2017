package ru.mail.park.gwent.mechanics;

import ru.mail.park.gwent.domains.game.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mechanics {
    private static final int NUMBER_OF_CARDS_ONE_TYPE = 8;
    private static final int NUMBER_OF_CARDS_IN_HAND = 10;

    private PlayField playField;

    public Mechanics() {
        playField = new PlayField();
    }

    public void firstPlayerGo(Card card) {
        final Deck hand = playField.getFirstPlayerHand();
        final PlayerLines lines = playField.getFirstPlayerLines();
        if (hand.hasCard(card)) {
            lines.addCartToLine(card);
        }
    }

    public static Deck genarateDeck() {
        final List<Card> cards = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CARDS_ONE_TYPE; i++) {
            final Card card = new Card(i + 1, CardType.FRONT);
            cards.add(card);
        }
        for (int i = 0; i < NUMBER_OF_CARDS_ONE_TYPE; i++) {
            final Card card = new Card(i + 1, CardType.BACK);
            cards.add(card);
        }
        for (int i = 0; i < NUMBER_OF_CARDS_ONE_TYPE; i++) {
            final Card card = new Card(i + 1, CardType.BASIS);
            cards.add(card);
        }

        return new Deck(cards);
    }

    public static Deck getHand(Deck deck) {
        Collections.shuffle(deck.getCards());

        final List<Card> handCards = deck.giveOutCards(NUMBER_OF_CARDS_IN_HAND);

        return new Deck(handCards);
    }


}
