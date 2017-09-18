package ru.mail.park.gwent.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.gwent.Message;
import ru.mail.park.gwent.account.AccountService;
import ru.mail.park.gwent.account.UserProfile;

@RestController
public class UserController {
    private final AccountService accountService;

    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/join")
    public ResponseEntity signUp(@RequestBody(required = false) UserProfile profile) {
        if (profile == null || profile.getLogin() == null || profile.getPassword() == null) {
            final Message noLoginOrPasswordMsg = new Message("No login or password");
            return ResponseEntity.badRequest().body(noLoginOrPasswordMsg);
        }

        if (profile.getLogin().isEmpty() || profile.getPassword().isEmpty()) {
            final Message emptyLoginOrPasswordMsg = new Message("Empty login or password");
            return ResponseEntity.badRequest().body(emptyLoginOrPasswordMsg);
        }

        final UserProfile findedUserByLogin = accountService.getUserByLogin(profile.getLogin());

        if (findedUserByLogin != null) {
            final Message loginIsAlreadyTakenMsg = new Message("Login is already taken");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(loginIsAlreadyTakenMsg);
        }

        accountService.addUser(profile);
        final Message signedUpMsg = new Message("User signed up");
        return ResponseEntity.ok().body(signedUpMsg);
    }
}
