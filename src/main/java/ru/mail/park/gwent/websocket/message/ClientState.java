package ru.mail.park.gwent.websocket.message;

public enum ClientState {
    INIT(0),
    WAITING_PLAYER(1),
    READY(2);

    private int id;

    ClientState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
