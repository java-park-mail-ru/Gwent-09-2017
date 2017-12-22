package ru.mail.park.gwent.domains.game;

import org.springframework.web.socket.WebSocketSession;
import ru.mail.park.gwent.websocket.message.ClientState;

import java.util.Objects;

public class WebSocketUserState {
    private WebSocketSession session;
    private ClientState state;

    public WebSocketUserState(WebSocketSession session) {
        this.session = session;
        state = ClientState.INIT;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public ClientState getState() {
        return state;
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WebSocketUserState that = (WebSocketUserState) obj;
        return Objects.equals(session, that.session) && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, state);
    }
}
