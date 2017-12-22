package ru.mail.park.gwent.domains.game;

public class PlayerLines {
    private int allPoints;

    private Line frontLine;
    private Line backLine;
    private Line basisLine;

    PlayerLines() {
        allPoints = 0;
        frontLine = new Line(CardType.FRONT);
        backLine = new Line(CardType.BACK);
        basisLine = new Line(CardType.BASIS);
    }

    public int recalcAllPoints() {
        allPoints = 0;
        allPoints += frontLine.recalcPoints();
        allPoints += backLine.recalcPoints();
        allPoints += basisLine.recalcPoints();

        return allPoints;
    }

    @SuppressWarnings("unused")
    public void clear() {
        frontLine.clearLine();
        backLine.clearLine();
        basisLine.clearLine();

        recalcAllPoints();
    }

    public void addCartToLine(Card card) {
        switch (card.getType()) {
            case FRONT:
                frontLine.addCard(card);
                break;
            case BACK:
                backLine.addCard(card);
                break;
            case BASIS:
                basisLine.addCard(card);
                break;
            default:
        }
    }
}
