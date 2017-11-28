package ru.mail.park.gwent.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.gwent.domains.auth.Message;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.services.UserService;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ru.mail.park.gwent.consts.Constants.SIGN_UP_URL;
import static ru.mail.park.gwent.domains.auth.MessageEnum.*;

/**
 * @author Konstantin Gulyy
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String LOGIN = "login";
    private static final String EMAIL = "email@email.ru";
    private static final String PASSWORD = "password";
    private static final String EMPTY_LOGIN = "";
    private static final String EMPTY_PASSWORD = "";

    @Test
    public void testSignUpEmptyBody() {
        final HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
        final HttpEntity<Void> request = new HttpEntity<>(headers);

        final ResponseEntity<Message> response = restTemplate.postForEntity(SIGN_UP_URL, request, Message.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(NO_LOGIN_OR_PASSWORD.getMessage(), response.getBody());
    }

    @Test
    public void testSignUpEmptyLoginAndPassword() {
        final UserProfile user = new UserProfile(EMPTY_LOGIN, EMPTY_PASSWORD, null);

        final ResponseEntity<Message> response = restTemplate.postForEntity(SIGN_UP_URL, user, Message.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(EMPTY_LOGIN_OR_PASSWORD.getMessage(), response.getBody());
    }

    @Test
    public void testSignUpConflictUser() {
        final UserProfile emptyUser = new UserProfile();
        when(userService.getUserByLogin(anyString())).thenReturn(emptyUser);

        final UserProfile newProfile = new UserProfile(LOGIN, PASSWORD, EMAIL);

        final ResponseEntity<Message> response = restTemplate.postForEntity(SIGN_UP_URL, newProfile, Message.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(LOGIN_IS_ALREADY_TAKEN.getMessage(), response.getBody());
        verify(userService).getUserByLogin(anyString());
    }

    @Test
    public void testSignUpSuccess() {
        final UserProfile newProfile = new UserProfile(LOGIN, PASSWORD, EMAIL);

        final ResponseEntity<Message> response = restTemplate.postForEntity(SIGN_UP_URL, newProfile, Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SIGNED_UP.getMessage(), response.getBody());
    }
}