package ru.mail.park.gwent.domains.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public class UserInfo {
    private String login;
    private String email;
    private Integer wins;
    private Integer position;

    @JsonCreator
    public UserInfo(@JsonProperty("login") String login,
                    @JsonProperty("email") String email) {
        this.login = login;
        this.email = email;
    }

    public UserInfo(@NotNull UserProfile profile) {
        login = profile.getLogin();
        email = profile.getEmail();
        wins = profile.getWins();
    }

    public UserInfo(UserProfile profile, Integer position) {
        this(profile);
        this.position = position;
    }

    @JsonGetter
    public String getLogin() {
        return login;
    }

    @JsonGetter
    public String getEmail() {
        return email;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getWins() {
        return wins;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getPosition() {
        return position;
    }
}
