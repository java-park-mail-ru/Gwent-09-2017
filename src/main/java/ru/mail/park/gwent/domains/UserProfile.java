package ru.mail.park.gwent.domains;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserProfile {
    @Id
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;

    public UserProfile() {
    }

    @JsonCreator
    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
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
    public String getPassword() {
        return password;
    }

    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
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
