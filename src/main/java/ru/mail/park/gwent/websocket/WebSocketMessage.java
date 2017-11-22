package ru.mail.park.gwent.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(WantPlayMessage.class),
        @JsonSubTypes.Type(GameInitMessage.class),
        @JsonSubTypes.Type(ExceptionMessage.class),
})
public class WebSocketMessage {
}
