package ru.mail.park.gwent.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.gwent.account.AccountService;
import ru.mail.park.gwent.account.UserProfile;
import ru.mail.park.gwent.controllers.messages.Message;

import javax.servlet.http.HttpSession;

import static ru.mail.park.gwent.controllers.messages.MessageEnum.*;

@CrossOrigin(origins = "https://testgwent.herokuapp.com")
@RestController
public class UserController {
    private final AccountService accountService;

    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/join")
    public ResponseEntity<Message> signUp(@RequestBody(required = false) UserProfile profile) {
        if (profile == null || profile.getLogin() == null || profile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD.getMessage());
        }

        if (profile.getLogin().isEmpty() || profile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD.getMessage());
        }

        final UserProfile findedUserByLogin = accountService.getUserByLogin(profile.getLogin());

        if (findedUserByLogin != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(LOGIN_IS_ALREADY_TAKEN.getMessage());
        }

        accountService.addUser(profile);
        return ResponseEntity.ok().body(SIGNED_UP.getMessage());
    }

    @PutMapping("/api/user")
    public ResponseEntity<Message> updateUserProfile(@RequestBody(required = false) UserProfile profile, HttpSession session) {
        if (profile == null || profile.getLogin() == null || profile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD.getMessage());
        }

        if (profile.getLogin().isEmpty() || profile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD.getMessage());
        }

        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = accountService.getUserBySessionId(sessionId);
        if (findedUserBySessionId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED.getMessage());
        }

        accountService.deleteUser(findedUserBySessionId.getLogin());
        accountService.addUser(profile);
        findedUserBySessionId.setProfile(profile);
        return ResponseEntity.ok().body(USER_PROFILE_UPDATED.getMessage());
    }
}
