package ru.mail.park.gwent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.controllers.messages.Message;
import ru.mail.park.gwent.services.SessionService;
import ru.mail.park.gwent.services.UserService;

import javax.servlet.http.HttpSession;

import static ru.mail.park.gwent.controllers.messages.MessageEnum.*;

@RestController
public class SessionController {
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    SessionController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/api/auth")
    public ResponseEntity getLoggedUserProfile(HttpSession session) {
        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = sessionService.getUserBySessionId(sessionId);
        if (findedUserBySessionId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED.getMessage());
        } else {
            return ResponseEntity.ok(findedUserBySessionId);
        }
    }

    @PostMapping("/api/auth")
    public ResponseEntity<Message> signIn(@RequestBody(required = false) UserProfile profile, HttpSession session) {
        if (profile == null || profile.getLogin() == null || profile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD.getMessage());
        }

        if (profile.getLogin().isEmpty() || profile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD.getMessage());
        }

        final UserProfile findedUserByLogin = userService.getUserByLogin(profile.getLogin());
        if (findedUserByLogin == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(WRONG_LOGIN_OR_PASSWORD.getMessage());
        }

        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = sessionService.getUserBySessionId(sessionId);
        if (findedUserBySessionId != null) {
            if (findedUserBySessionId.equals(findedUserByLogin)) {
                // пользователь авторизован и пытается авторизоваться под своим именем еще раз
                return ResponseEntity.ok().body(ALREADY_AUTHORIZED.getMessage());
            } else {
                // пользователь авторизован и пытается авторизоваться под другим именем
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ANOTHER_ALREADY_AUTHORIZED.getMessage());
            }
        }

        if (!findedUserByLogin.getPassword().equals(profile.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(WRONG_LOGIN_OR_PASSWORD.getMessage());
        }

        sessionService.addSession(sessionId, findedUserByLogin);
        return ResponseEntity.ok().body(AUTHORIZED.getMessage());
    }

    @DeleteMapping("/api/auth")
    public ResponseEntity<Message> signOut(HttpSession session) {
        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = sessionService.getUserBySessionId(sessionId);

        if (findedUserBySessionId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED.getMessage());
        }

        sessionService.deleteSession(sessionId);
        return ResponseEntity.ok().body(LOGGED_OUT.getMessage());
    }
}
