package ru.mail.park.gwent.mechanics;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.gwent.domains.game.Card;
import ru.mail.park.gwent.domains.game.Player;
import ru.mail.park.gwent.websocket.message.client.StepMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class GameMechanicsTest {
    @Autowired
    private GameMechanics gameMechanics;
    @Autowired
    private GameSessionService gameSessionService;
    @NotNull
    private Player player1;
    @NotNull
    private Player player2;

    @Before
    public void setUp() {
        player1 = new Player("player1", "player1@my.com");
        player2 = new Player("player2", "player2@my.com");
        startGame(player1, player2);
    }

    private GameSession startGame(@NotNull Player player1, @NotNull Player player2) {
        gameMechanics.joinPlayer(player1);
        gameMechanics.joinPlayer(player2);
        gameMechanics.startGame();
        final GameSession gameSession = gameSessionService.getSessionForUser(player1);

        Assert.assertNotNull("GameMechanics session should be started", gameSession);

        return gameSession;
    }

    @Test
    public void startGameTest() {
        startGame(player1, player2);
    }

    @Test
    public void simpleStepTest() {
        final GameSession gameSession = gameSessionService.getSessionForUser(player1);

        Assert.assertEquals(10, gameSession.getPlayField().getFirstPlayerHand().getCards().size());
        Assert.assertEquals(0, gameSession.getPlayField().getFirstPlayerLines().recalcAllPoints());

        final Card firstCard = gameSession.getPlayField().getFirstPlayerHand().getCards().get(0);
        gameMechanics.step(player1, new StepMessage(firstCard));
        Assert.assertEquals(9, gameSession.getPlayField().getFirstPlayerHand().getCards().size());
        Assert.assertEquals(firstCard.getPoints(), gameSession.getPlayField().getFirstPlayerLines().recalcAllPoints());
    }

    @Test
    public void stepOpponentTest() {
        final GameSession gameSession = gameSessionService.getSessionForUser(player2);

        Assert.assertEquals(10, gameSession.getPlayField().getSecondPlayerHand().getCards().size());
        Assert.assertEquals(0, gameSession.getPlayField().getSecondPlayerLines().recalcAllPoints());

        final Card secondCard = gameSession.getPlayField().getSecondPlayerHand().getCards().get(0);
        gameMechanics.stepOpponent(player2, new StepMessage(secondCard));
        Assert.assertEquals(9, gameSession.getPlayField().getSecondPlayerHand().getCards().size());
        Assert.assertEquals(secondCard.getPoints(), gameSession.getPlayField().getSecondPlayerLines().recalcAllPoints());
    }
}
