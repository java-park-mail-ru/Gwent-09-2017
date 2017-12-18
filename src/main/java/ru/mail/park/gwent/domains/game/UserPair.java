package ru.mail.park.gwent.domains.game;

import ru.mail.park.gwent.domains.auth.UserProfile;

public class UserPair {
    private UserProfile firstUser;
    private UserProfile secondUser;

    public UserPair(UserProfile firstUser, UserProfile secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public UserProfile getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(UserProfile firstUser) {
        this.firstUser = firstUser;
    }

    public UserProfile getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(UserProfile secondUser) {
        this.secondUser = secondUser;
    }
}
