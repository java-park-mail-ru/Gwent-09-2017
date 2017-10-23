package ru.mail.park.gwent.controllers;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.gwent.domains.Message;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ru.mail.park.gwent.domains.MessageEnum.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final HttpHeaders REQUEST_HEADERS = new HttpHeaders();
    private static final String LOGIN = "login";
    private static final String EMAIL = "email@email.ru";
    private static final String PASSWORD = "password";

    @BeforeClass
    public static void setHttpHeaders() {
        final List<String> origin = Collections.singletonList("http://localhost:8000");
        REQUEST_HEADERS.put(HttpHeaders.ORIGIN, origin);
        final List<String> contentType = Collections.singletonList("application/json");
        REQUEST_HEADERS.put(HttpHeaders.CONTENT_TYPE, contentType);
    }

    @Test
    public void testSignUpEmptyBody() {
        final HttpEntity<UserProfile> requestEntity = new HttpEntity<>(REQUEST_HEADERS);

        final ResponseEntity<Message> response = restTemplate.postForEntity("/api/join", requestEntity, Message.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(NO_LOGIN_OR_PASSWORD.getMessage(), response.getBody());
    }

    @Test
    public void testSignUpEmptyLoginAndPassword() {
        final UserProfile user = new UserProfile("", "", null);
        final HttpEntity<UserProfile> requestEntity = new HttpEntity<>(user, REQUEST_HEADERS);

        final ResponseEntity<Message> response = restTemplate.postForEntity("/api/join", requestEntity, Message.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(EMPTY_LOGIN_OR_PASSWORD.getMessage(), response.getBody());
    }

    @Test
    public void testSignUpConflictUser() {
        final UserProfile emptyUser = new UserProfile();
        when(userService.getUserByLogin(anyString())).thenReturn(emptyUser);

        final UserProfile newProfile = new UserProfile(LOGIN, PASSWORD, EMAIL);
        final HttpEntity<UserProfile> requestEntity = new HttpEntity<>(newProfile, REQUEST_HEADERS);

        final ResponseEntity<Message> response = restTemplate.postForEntity("/api/join", requestEntity, Message.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(LOGIN_IS_ALREADY_TAKEN.getMessage(), response.getBody());
        verify(userService).getUserByLogin(anyString());
    }

    @Test
    public void testSignUpSuccess() {
        final UserProfile newProfile = new UserProfile(LOGIN, PASSWORD, EMAIL);
        final HttpEntity<UserProfile> requestEntity = new HttpEntity<>(newProfile, REQUEST_HEADERS);

        final ResponseEntity<Message> response = restTemplate.postForEntity("/api/join", requestEntity, Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SIGNED_UP.getMessage(), response.getBody());
    }
}