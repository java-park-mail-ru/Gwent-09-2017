package ru.mail.park.gwent.domains;

import com.fasterxml.jackson.annotation.*;

public class UserInfo {
    private String login;
    private String email;

    @JsonCreator
    public UserInfo(@JsonProperty("login") String login,
                    @JsonProperty("email") String email) {
        this.login = login;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }
}
