package ru.mail.park.gwent.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.gwent.account.AccountService;
import ru.mail.park.gwent.account.UserProfile;

@RestController
public class UserController {
    private final AccountService accountService;

    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/user")
    public UserProfile getUserProfile() {
        return new UserProfile("test","password", "test@my.com");
    }

    @PostMapping("/user")
    public HttpStatus signUp(@RequestBody UserProfile profile) {
        final String login = profile.getLogin();
        final String password = profile.getPassword();
        final String email = profile.getEmail();

        if(login == null || password == null) {
            return HttpStatus.BAD_REQUEST;
        }

        UserProfile user = accountService.getUserByLogin(login);
        if(user == null) {
            user = new UserProfile(login, password, email);
            accountService.addUser(user);
            return HttpStatus.OK;
        } else {
            return HttpStatus.CONFLICT;
        }
    }
}
