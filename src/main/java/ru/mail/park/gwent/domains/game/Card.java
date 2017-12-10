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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Card card = (Card) obj;

        if (points != card.points) {
            return false;
        }
        return type == card.type;
    }

    @Override
    public int hashCode() {
        final int primaryNumber = 31;
        int result = points;
        result = primaryNumber * result + type.hashCode();
        return result;
    }
}
