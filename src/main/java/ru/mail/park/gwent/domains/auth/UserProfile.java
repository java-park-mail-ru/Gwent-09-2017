package ru.mail.park.gwent.domains.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "users")
public class UserProfile {
    @Id
    private String login;
    private String password;
    private String email;

    @Convert(converter = AtomicIntConverter.class)
    private AtomicInteger wins;

    public UserProfile() {
    }

    @JsonCreator
    public UserProfile(
            @JsonProperty("login") String login,
            @JsonProperty("password") String password,
            @Nullable @JsonProperty("email") String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        wins = new AtomicInteger(0);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWins() {
        return wins.get();
    }

    public void incWins() {
        wins.getAndIncrement();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final UserProfile user = (UserProfile) obj;

        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
