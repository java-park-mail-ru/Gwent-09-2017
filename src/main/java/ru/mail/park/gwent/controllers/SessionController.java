package ru.mail.park.gwent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.gwent.domains.Message;
import ru.mail.park.gwent.domains.UserInfo;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.UserService;

import javax.servlet.http.HttpSession;

import static ru.mail.park.gwent.domains.MessageEnum.*;

@RestController
@RequestMapping("/api/auth")
public class SessionController {
    public static final String SESSION_KEY = "UserProfile";

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    SessionController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping
    public ResponseEntity getLoggedUserProfile(HttpSession session) {
        final UserProfile foundUserBySession = (UserProfile) session.getAttribute(SESSION_KEY);
        if (foundUserBySession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED.getMessage());
        } else {
            final UserInfo userInfo = new UserInfo(foundUserBySession.getLogin(), foundUserBySession.getEmail());
            return ResponseEntity.ok(userInfo);
        }
    }

    @PostMapping
    public ResponseEntity<Message> signIn(@RequestBody(required = false) UserProfile profile, HttpSession session) {
        if (profile == null || profile.getLogin() == null || profile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD.getMessage());
        }

        if (profile.getLogin().isEmpty() || profile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD.getMessage());
        }

        final UserProfile foundUserByLogin = userService.getUserByLogin(profile.getLogin());
        if (foundUserByLogin == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(WRONG_LOGIN_OR_PASSWORD.getMessage());
        }

        final UserProfile foundUserBySession = (UserProfile) session.getAttribute(SESSION_KEY);
        if (foundUserBySession != null) {
            if (foundUserBySession.equals(foundUserByLogin)) {
                // пользователь авторизован и пытается авторизоваться под своим именем еще раз
                return ResponseEntity.ok().body(ALREADY_AUTHORIZED.getMessage());
            } else {
                // пользователь авторизован и пытается авторизоваться под другим именем
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ANOTHER_ALREADY_AUTHORIZED.getMessage());
            }
        }

        if (!encoder.matches(profile.getPassword(), foundUserByLogin.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(WRONG_LOGIN_OR_PASSWORD.getMessage());
        }

        session.setAttribute(SESSION_KEY, foundUserByLogin);
        return ResponseEntity.ok().body(AUTHORIZED.getMessage());
    }

    @DeleteMapping
    public ResponseEntity<Message> signOut(HttpSession session) {
        final UserProfile foundUserBySession = (UserProfile) session.getAttribute(SESSION_KEY);

        if (foundUserBySession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED.getMessage());
        }

        session.removeAttribute(SESSION_KEY);
        session.invalidate();

        return ResponseEntity.ok().body(LOGGED_OUT.getMessage());
    }
}
