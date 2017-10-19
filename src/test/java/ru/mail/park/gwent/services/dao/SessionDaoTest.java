package ru.mail.park.gwent.services.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.SessionService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionDaoTest {
    private static final String SESSION_ID = "test_session_id";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email@email.ru";
    private static final String PASSWORD = "password";
    private static final String UPDATED_EMAIL = "updated@email.ru";
    private static final String UPDATED_PASSWORD = "updated_password";

    @Autowired
    private SessionService sessionService;

    @Test
    public void testAddAndGetUserSession() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);

        sessionService.addSession(SESSION_ID, user);
        final UserProfile sessionUser = sessionService.getUserBySessionId(SESSION_ID);

        assertNotNull(sessionUser);
        assertEquals(user, sessionUser);
    }

    @Test
    public void testDeleteSession() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);
        sessionService.addSession(SESSION_ID, user);

        sessionService.deleteSession(SESSION_ID);
        final UserProfile foundedUser = sessionService.getUserBySessionId(SESSION_ID);

        assertNull(foundedUser);
    }

    @Test
    public void testUpdateSession() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);
        sessionService.addSession(SESSION_ID, user);
        user.setPassword(UPDATED_PASSWORD);
        user.setEmail(UPDATED_EMAIL);

        sessionService.updateSession(SESSION_ID, user);
        final UserProfile sessionUser = sessionService.getUserBySessionId(SESSION_ID);

        assertNotNull(sessionUser);
        assertEquals(user, sessionUser);
    }

}