package ru.mail.park.gwent.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserInfo {
    private String login;
    private String email;

    @JsonCreator
    public UserInfo(String login, String email) {
        this.login = login;
        this.email = email;
    }

    @JsonGetter
    public String getLogin() {
        return login;
    }

    @JsonSetter
    public void setLogin(String login) {
        this.login = login;
    }

    @JsonGetter
    public String getEmail() {
        return email;
    }

    @JsonSetter
    public void setEmail(String email) {
        this.email = email;
    }
}
