package ru.mail.park.gwent.websocket.message;

public enum ClientState {
    INIT,
    WAITING_PLAYER,
    READY,
    PLAY,
    WAITING_STEP,
    WAITING_PASS,
    ROUND_END,
    GAME_OVER
}
