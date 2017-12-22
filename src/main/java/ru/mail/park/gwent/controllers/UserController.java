package ru.mail.park.gwent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.gwent.domains.auth.Message;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.domains.game.Player;
import ru.mail.park.gwent.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static ru.mail.park.gwent.consts.Constants.*;
import static ru.mail.park.gwent.domains.auth.MessageEnum.*;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping(SIGN_UP_URL)
    public ResponseEntity<Message> signUp(@RequestBody(required = false) UserProfile newProfile) {
        if (newProfile == null || newProfile.getLogin() == null || newProfile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD.getMessage());
        }

        if (newProfile.getLogin().isEmpty() || newProfile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD.getMessage());
        }

        final UserProfile foundUserByLogin = userService.getUserProfile(newProfile.getLogin());

        if (foundUserByLogin != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(LOGIN_IS_ALREADY_TAKEN.getMessage());
        }

        newProfile.setPassword(encoder.encode(newProfile.getPassword()));
        userService.createUser(newProfile);

        return ResponseEntity.ok().body(SIGNED_UP.getMessage());
    }

    @GetMapping(USERS_URL)
    public ResponseEntity<?> getUsersOnScoreBoard(
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "1") int offset) {
        if (offset < 1) {
            return ResponseEntity.badRequest().body(OFFSET_MUST_BE_OVER_THAN_ZERO.getMessage());
        }

        final List<Player> players = userService.getPlayers(limit, offset);

        if (players.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_LIST_IS_EMPTY.getMessage());
        }

        return ResponseEntity.ok(players);
    }

    @PostMapping(USERS_URL)
    public ResponseEntity<?> incrementWins(HttpSession session) {
        final UserProfile foundUserBySession = (UserProfile) session.getAttribute(SESSION_USER_PROFILE_KEY);

        if (foundUserBySession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED.getMessage());
        }

        userService.incWins(foundUserBySession);

        return ResponseEntity.ok(foundUserBySession);
    }
}
