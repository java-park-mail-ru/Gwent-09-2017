package ru.mail.park.gwent.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.gwent.account.AccountService;
import ru.mail.park.gwent.account.UserProfile;

import static ru.mail.park.gwent.controllers.messages.MessageEnum.*;

@RestController
public class UserController {
    private final AccountService accountService;

    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/join")
    public ResponseEntity signUp(@RequestBody(required = false) UserProfile profile) {
        if (profile == null || profile.getLogin() == null || profile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD_MSG);
        }

        if (profile.getLogin().isEmpty() || profile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD_MSG);
        }

        final UserProfile findedUserByLogin = accountService.getUserByLogin(profile.getLogin());

        if (findedUserByLogin != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(LOGIN_IS_ALREADY_TAKEN_MSG);
        }

        accountService.addUser(profile);
        return ResponseEntity.ok().body(SIGNED_UP_MSG);
    }
}
