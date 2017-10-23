package ru.mail.park.gwent.domains;

import com.fasterxml.jackson.annotation.*;

public class UserInfo {
    @JsonProperty("login")
    private String login;
    @JsonProperty("email")
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
