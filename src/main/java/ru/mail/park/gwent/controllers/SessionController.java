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
    private final AccountService accountService;

    public SessionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/auth")
    public ResponseEntity getLoggedUserProfile(HttpSession session) {
        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = accountService.getUserBySessionId(sessionId);
        if (findedUserBySessionId == null) {
            final Message notAuthorizedMsg = new Message("User not authorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(notAuthorizedMsg);
        } else {
            return ResponseEntity.ok(findedUserBySessionId);
        }
    }

    @PostMapping("/api/auth")
    public ResponseEntity signIn(@RequestBody(required = false) UserProfile profile, HttpSession session) {
        if (profile == null || profile.getLogin() == null || profile.getPassword() == null) {
            final Message noLoginOrPasswordMsg = new Message("No login or password");
            return ResponseEntity.badRequest().body(noLoginOrPasswordMsg);
        }

        if (profile.getLogin().isEmpty() || profile.getPassword().isEmpty()) {
            final Message emptyLoginOrPasswordMsg = new Message("Empty login or password");
            return ResponseEntity.badRequest().body(emptyLoginOrPasswordMsg);
        }

        final UserProfile findedUserByLogin = accountService.getUserByLogin(profile.getLogin());

        if (findedUserByLogin == null || !findedUserByLogin.getPassword().equals(profile.getPassword())) {
            final Message wrongLoginOrPasswordMsg = new Message("Wrong login or password");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(wrongLoginOrPasswordMsg);
        }

        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = accountService.getUserBySessionId(sessionId);
        if (findedUserBySessionId != null) {
            final Message alreadyAuthorizedMsg = new Message("User is already authorized");
            return ResponseEntity.ok().body(alreadyAuthorizedMsg);
        }

        accountService.addSession(sessionId, findedUserByLogin);
        final Message authorizedMsg = new Message("User authorized");
        return ResponseEntity.ok().body(authorizedMsg);
    }

    @DeleteMapping("/api/auth")
    public ResponseEntity signOut(HttpSession session) {
        final String sessionId = session.getId();
        final UserProfile findedUserBySessionId = accountService.getUserBySessionId(sessionId);

        if (findedUserBySessionId == null) {
            final Message notAuthorizedMsg = new Message("User not authorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(notAuthorizedMsg);
        }

        accountService.deleteSession(sessionId);
        final Message loggedOutMsg = new Message("User logged out");
        return ResponseEntity.ok().body(loggedOutMsg);
    }
}
