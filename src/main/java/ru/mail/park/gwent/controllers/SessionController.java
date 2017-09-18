package ru.mail.park.gwent.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.gwent.Message;
import ru.mail.park.gwent.account.AccountService;
import ru.mail.park.gwent.account.UserProfile;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {
    private static final Message AUTHORIZED_MSG = new Message("User authorized");
    private static final Message LOGGED_OUT_MSG = new Message("User logged out");
    private static final Message NOT_AUTHORIZED_MSG = new Message("User not authorized");
    private static final Message ALREADY_AUTHORIZED_MSG = new Message("User is already authorized");
    private static final Message NO_LOGIN_OR_PASSWORD_MSG = new Message("No login or password");
    private static final Message EMPTY_LOGIN_OR_PASSWORD_MSG = new Message("Empty login or password");
    private static final Message WRONG_LOGIN_OR_PASSWORD_MSG = new Message("Wrong login or password");
    private static final Message ANOTHER_ALREADY_AUTHORIZED_MSG =
            new Message("Another user is already authorized, try to logout and login again");

    private final AccountService accountService;

    public SessionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/auth")
    public ResponseEntity getLoggedUserProfile(HttpSession session) {
        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = accountService.getUserBySessionId(sessionId);
        if (findedUserBySessionId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED_MSG);
        } else {
            return ResponseEntity.ok(findedUserBySessionId);
        }
    }

    @PostMapping("/api/auth")
    public ResponseEntity signIn(@RequestBody(required = false) UserProfile profile, HttpSession session) {
        if (profile == null || profile.getLogin() == null || profile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD_MSG);
        }

        if (profile.getLogin().isEmpty() || profile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD_MSG);
        }

        final UserProfile findedUserByLogin = accountService.getUserByLogin(profile.getLogin());

        if (findedUserByLogin == null || !findedUserByLogin.getPassword().equals(profile.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(WRONG_LOGIN_OR_PASSWORD_MSG);
        }

        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = accountService.getUserBySessionId(sessionId);
        if (findedUserBySessionId != null) {
            if (findedUserBySessionId.equals(findedUserByLogin)) {
                // пользователь авторизован и пытается авторизоваться под своим именем еще раз
                return ResponseEntity.ok().body(ALREADY_AUTHORIZED_MSG);
            } else {
                // пользователь авторизован и пытается авторизоваться под другим именем
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ANOTHER_ALREADY_AUTHORIZED_MSG);
            }
        }

        accountService.addSession(sessionId, findedUserByLogin);
        return ResponseEntity.ok().body(AUTHORIZED_MSG);
    }

    @DeleteMapping("/api/auth")
    public ResponseEntity signOut(HttpSession session) {
        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = accountService.getUserBySessionId(sessionId);

        if (findedUserBySessionId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED_MSG);
        }

        accountService.deleteSession(sessionId);
        return ResponseEntity.ok().body(LOGGED_OUT_MSG);
    }
}
