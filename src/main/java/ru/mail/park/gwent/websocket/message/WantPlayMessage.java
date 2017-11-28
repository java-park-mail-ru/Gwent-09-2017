package ru.mail.park.gwent.websocket.message;

public class WantPlayMessage extends WebSocketMessage {
    private ClientState state;

    public WantPlayMessage() {
        state = ClientState.INIT;
    }

    public ClientState getState() {
        return state;
    }
}
