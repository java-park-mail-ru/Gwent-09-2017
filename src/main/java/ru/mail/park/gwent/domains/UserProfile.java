package ru.mail.park.gwent.domains;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserProfile {
    @Id
    private String login;
    private String password;
    private String email;

    public UserProfile() {
    }

    @JsonCreator
    public UserProfile(
            @JsonProperty("login") String login,
            @JsonProperty("password") String password,
            @JsonProperty("email") String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @JsonGetter
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonGetter
    public String getPassword() {
        return password;
    }

    @JsonGetter
    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final UserProfile userProfile = (UserProfile) obj;

        return login.equals(userProfile.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
