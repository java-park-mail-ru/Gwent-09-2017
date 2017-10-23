package ru.mail.park.gwent.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.gwent.domains.Message;
import ru.mail.park.gwent.domains.UserInfo;
import ru.mail.park.gwent.domains.UserProfile;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ru.mail.park.gwent.domains.MessageEnum.NOT_AUTHORIZED;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SessionControllerTest {
    @Mock
    private HttpSession session;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String AUTH_URL = "/api/auth";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email@email.ru";
    private static final String PASSWORD = "password";

    @Test
    public void testGetLoggedUserUnauthorized() {
        when(session.getAttribute(anyString())).thenReturn(null);

        final ResponseEntity<Message> response = restTemplate.getForEntity(AUTH_URL, Message.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(NOT_AUTHORIZED.getMessage(), response.getBody());
        verify(session).getAttribute(anyString());
    }

    @Test
    public void testGetLoggedUserSuccess() {
        final UserProfile foundUserProfile = new UserProfile(LOGIN, PASSWORD, EMAIL);
        when(session.getAttribute(anyString())).thenReturn(foundUserProfile);

        final ResponseEntity<UserInfo> response = restTemplate.getForEntity(AUTH_URL, UserInfo.class);
        final UserInfo userInfo = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(userInfo);
        assertEquals(LOGIN, userInfo.getLogin());
        assertEquals(EMAIL, userInfo.getEmail());
    }
}