package ru.mail.park.gwent.domains.game;

public class Card {
    private int points;
    private CardType type;

    public Card(int points, CardType type) {
        this.points = points;
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }
}
