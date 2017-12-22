package ru.mail.park.gwent.domains.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import ru.mail.park.gwent.domains.auth.UserProfile;

public class Player {
    private String login;
    private String email;
    private Integer wins;
    private Integer position;

    @JsonCreator
    public Player(@JsonProperty("login") String login,
                  @JsonProperty("email") String email) {
        this.login = login;
        this.email = email;
    }

    public Player(@NotNull UserProfile profile) {
        login = profile.getLogin();
        email = profile.getEmail();
        wins = profile.getWins();
    }

    public Player(UserProfile profile, Integer position) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Player player = (Player) obj;

        return login.equals(player.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
