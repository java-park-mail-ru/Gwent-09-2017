package ru.mail.park.gwent.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import ru.mail.park.gwent.websocket.message.WebSocketMessage;
import ru.mail.park.gwent.websocket.message.client.JoinGameMessage;

import java.io.IOException;

public class MessageSerializationTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void joinGameTest() throws IOException {
        final JoinGameMessage request = new JoinGameMessage();
        final String requestJson = objectMapper.writeValueAsString(request);
        final WebSocketMessage fromJson = objectMapper.readValue(requestJson, WebSocketMessage.class);
        Assert.assertTrue(fromJson instanceof JoinGameMessage);
    }
}
