package ru.mail.park.gwent.websocket.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.mail.park.gwent.websocket.message.client.GameInitMessage;
import ru.mail.park.gwent.websocket.message.server.ExceptionMessage;
import ru.mail.park.gwent.websocket.message.server.ReadyMessage;
import ru.mail.park.gwent.websocket.message.server.WaitingPlayerMessage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(GameInitMessage.class),
        @JsonSubTypes.Type(WaitingPlayerMessage.class),
        @JsonSubTypes.Type(ReadyMessage.class),
        @JsonSubTypes.Type(ExceptionMessage.class),
})
public class WebSocketMessage {
}
