package ru.mail.park.gwent.domains.game;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private CardType type;
    private int sumPoints;
    private List<Card> lineCards;

    Line(CardType type) {
        this.type = type;
        sumPoints = 0;
        lineCards = new ArrayList<>();
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void addCard(Card card) {
        lineCards.add(card);
        recalcPoints();
    }

    public void clearLine() {
        lineCards.clear();
        recalcPoints();
    }

    public int recalcPoints() {
        sumPoints = 0;
        for (Card card : lineCards) {
            sumPoints += card.getPoints();
        }
        return sumPoints;
    }
}
