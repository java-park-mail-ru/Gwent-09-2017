package ru.mail.park.gwent.domains.game;

import org.jetbrains.annotations.Nullable;

public class UserPair {
    private WebSocketUser firstUser;
    private WebSocketUser secondUser;

    public UserPair(WebSocketUser firstUser, @Nullable WebSocketUser secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public WebSocketUser getFirstUser() {
        return firstUser;
    }

    public WebSocketUser getSecondUser() {
        return secondUser;
    }
}
