package ru.mail.park.gwent.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.gwent.account.AccountService;
import ru.mail.park.gwent.account.UserProfile;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {
    private final AccountService accountService;

    public SessionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<UserProfile> getLoggedUserProfile(HttpSession session) {
        final String sessionId = session.getId();
        final UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            return ResponseEntity.ok(profile);
        }
    }

    @PostMapping("/")
    public HttpStatus signIn(@RequestBody UserProfile profile, HttpSession session) {
        final String login = profile.getLogin();
        final String password = profile.getPassword();

        if (login == null && password == null) {
            return HttpStatus.BAD_REQUEST;
        }

        final UserProfile user = accountService.getUserByLogin(login);
        if(user == null || !user.getPassword().equals(password)) {
            return HttpStatus.UNAUTHORIZED;
        }

        accountService.addSession(session.getId(), user);
        return HttpStatus.OK;
    }

    @DeleteMapping("/")
    public HttpStatus signOut(HttpSession session) {
        final String sessionId = session.getId();
        final UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            return HttpStatus.UNAUTHORIZED;
        } else {
            accountService.deleteSession(sessionId);
            return HttpStatus.OK;
        }
    }
}
