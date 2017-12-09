package ru.mail.park.gwent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.gwent.domains.auth.Message;
import ru.mail.park.gwent.domains.auth.UserInfo;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static ru.mail.park.gwent.consts.Constants.SIGN_UP_URL;
import static ru.mail.park.gwent.consts.Constants.USERS_URL;
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

        final UserProfile foundUserByLogin = userService.getUserByLogin(newProfile.getLogin());

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
            @RequestParam(required = false, defaultValue = "0") int offset) {

        final List<UserProfile> users = userService.getUsers(limit, offset);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final List<UserInfo> result = new ArrayList<>();
        for (UserProfile profile : users) {
            result.add(new UserInfo(profile));
        }

        return ResponseEntity.ok(result);
    }
}
