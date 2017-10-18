package ru.mail.park.gwent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/api/join")
    public ResponseEntity<Message> signUp(@RequestBody(required = false) UserProfile newProfile) {
        if (newProfile == null || newProfile.getLogin() == null || newProfile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD.getMessage());
        }

        if (newProfile.getLogin().isEmpty() || newProfile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD.getMessage());
        }

        final UserProfile findedUserByLogin = userService.getUserByLogin(newProfile.getLogin());

        if (findedUserByLogin != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(LOGIN_IS_ALREADY_TAKEN.getMessage());
        }

        userService.createUser(newProfile);
        return ResponseEntity.ok().body(SIGNED_UP.getMessage());
    }

    @PutMapping("/api/user")
    public ResponseEntity<Message> updateUserProfile(@RequestBody(required = false) UserProfile updatedProfile, HttpSession session) {
        if (updatedProfile == null || updatedProfile.getLogin() == null || updatedProfile.getPassword() == null) {
            return ResponseEntity.badRequest().body(NO_LOGIN_OR_PASSWORD.getMessage());
        }

        if (updatedProfile.getLogin().isEmpty() || updatedProfile.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(EMPTY_LOGIN_OR_PASSWORD.getMessage());
        }

        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = sessionService.getUserBySessionId(sessionId);
        if (findedUserBySessionId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_AUTHORIZED.getMessage());
        }

        final String currentLogin = findedUserBySessionId.getLogin();
        final String updatedProfileLogin = updatedProfile.getLogin();
        if (!currentLogin.equals(updatedProfileLogin)) {
            return ResponseEntity.badRequest().body(LOGIN_IS_NOT_THE_SAME.getMessage());
        }

        userService.updateUser(currentLogin, updatedProfile);
        sessionService.updateSession(sessionId, updatedProfile);
        return ResponseEntity.ok().body(USER_PROFILE_UPDATED.getMessage());
    }
}
