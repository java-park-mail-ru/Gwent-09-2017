package ru.mail.park.gwent.domains.game;

public class PlayerLines {
    private int allPoints;

    private Line frontLine;
    private Line middleLine;
    private Line backLine;

    public PlayerLines() {
        allPoints = 0;
        frontLine = new Line(CardType.FRONT);
        middleLine = new Line(CardType.MIDDLE);
        backLine = new Line(CardType.BACK);
    }

    public int getAllPoints() {
        return allPoints;
    }

    public int recalcAllPoints() {
        allPoints = 0;
        allPoints += frontLine.recalcPoints();
        allPoints += middleLine.recalcPoints();
        allPoints += backLine.recalcPoints();

        return allPoints;
    }

    public void clear() {
        frontLine.clearLine();
        middleLine.clearLine();
        backLine.clearLine();

        recalcAllPoints();
    }

    public void addCartToLine(Card card) {
        switch (card.getType()) {
            case FRONT:
                frontLine.addCard(card);
                break;
            case MIDDLE:
                middleLine.addCard(card);
                break;
            case BACK:
                backLine.addCard(card);
                break;
            default:
        }
    }
}
