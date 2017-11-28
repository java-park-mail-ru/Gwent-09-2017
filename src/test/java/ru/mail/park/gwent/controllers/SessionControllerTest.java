package ru.mail.park.gwent.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.gwent.domains.auth.Message;
import ru.mail.park.gwent.domains.auth.UserInfo;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.services.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ru.mail.park.gwent.consts.Constants.AUTH_URL;
import static ru.mail.park.gwent.consts.Constants.LOCALHOST;
import static ru.mail.park.gwent.domains.auth.MessageEnum.*;

/**
 * @author Konstantin Gulyy
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SessionControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String LOGIN = "login";
    private static final String EMAIL = "email@email.ru";
    private static final String PASSWORD = "password";
    private static final String EMPTY_LOGIN = "";
    private static final String EMPTY_PASSWORD = "";
    private static final String WRONG_PASSWORD = "wrong-password";

    private HttpHeaders setUpHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();

        final List<String> origin = Collections.singletonList(LOCALHOST);
        headers.put(HttpHeaders.ORIGIN, origin);
        final List<String> contentType = Collections.singletonList("application/json");
        headers.put(HttpHeaders.CONTENT_TYPE, contentType);

        return headers;
    }

    @Test
    public void testGetLoggedUserUnauthorized() {
        final ResponseEntity<Message> response = restTemplate.getForEntity(AUTH_URL, Message.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(NOT_AUTHORIZED.getMessage(), response.getBody());
    }

    @Test
    public void testSignInEmptyBody() {
        final HttpHeaders headers = setUpHttpHeaders();
        final HttpEntity<UserProfile> request = new HttpEntity<>(headers);

        final ResponseEntity<Message> response = restTemplate.postForEntity(AUTH_URL, request, Message.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(NO_LOGIN_OR_PASSWORD.getMessage(), response.getBody());
    }

    @Test
    public void testSignInEmptyLoginAndPassword() {
        final UserProfile user = new UserProfile(EMPTY_LOGIN, EMPTY_PASSWORD, null);

        final ResponseEntity<Message> response = restTemplate.postForEntity(AUTH_URL, user, Message.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(EMPTY_LOGIN_OR_PASSWORD.getMessage(), response.getBody());
    }

    @Test
    public void testSingInWrongLogin() {
        when(userService.getUserByLogin(anyString())).thenReturn(null);
        final UserProfile login = new UserProfile(LOGIN, PASSWORD, EMAIL);

        final ResponseEntity<Message> loginResponse = restTemplate.postForEntity(AUTH_URL, login, Message.class);

        verify(userService).getUserByLogin(anyString());
        assertEquals(HttpStatus.FORBIDDEN, loginResponse.getStatusCode());
        assertEquals(WRONG_LOGIN_OR_PASSWORD.getMessage(), loginResponse.getBody());
    }

    @Test
    public void testSingInWrongPassword() {
        final UserProfile userMock = new UserProfile(LOGIN, passwordEncoder.encode(PASSWORD), EMAIL);
        when(userService.getUserByLogin(eq(LOGIN))).thenReturn(userMock);

        final UserProfile login = new UserProfile(LOGIN, WRONG_PASSWORD, EMAIL);
        final ResponseEntity<Message> loginResponse = restTemplate.postForEntity(AUTH_URL, login, Message.class);

        verify(userService).getUserByLogin(eq(LOGIN));
        assertEquals(HttpStatus.FORBIDDEN, loginResponse.getStatusCode());
        assertEquals(WRONG_LOGIN_OR_PASSWORD.getMessage(), loginResponse.getBody());
    }

    @Test
    public void testLogoutUnauthorized() {
        final ResponseEntity<Message> response = restTemplate.exchange(AUTH_URL, HttpMethod.DELETE, null, Message.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(NOT_AUTHORIZED.getMessage(), response.getBody());
    }

    @Test
    public void testLoginSuccess() {
        final UserProfile userMock = new UserProfile(LOGIN, passwordEncoder.encode(PASSWORD), EMAIL);
        when(userService.getUserByLogin(eq(LOGIN))).thenReturn(userMock);

        final UserProfile login = new UserProfile(LOGIN, PASSWORD, EMAIL);
        final ResponseEntity<Message> loginResponse = restTemplate.postForEntity(AUTH_URL, login, Message.class);

        verify(userService).getUserByLogin(eq(LOGIN));
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertEquals(AUTHORIZED.getMessage(), loginResponse.getBody());

        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, loginResponse.getHeaders().get("Set-Cookie"));
        final HttpEntity<Void> request = new HttpEntity<>(requestHeaders);

        final ResponseEntity<UserInfo> response = restTemplate.exchange(AUTH_URL, HttpMethod.GET, request, UserInfo.class);

        final UserInfo userInfo = response.getBody();

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(userInfo);
        assertEquals(LOGIN, userInfo.getLogin());
        assertEquals(EMAIL, userInfo.getEmail());
    }

    @Test
    public void testLogoutSuccess() {
        final UserProfile userMock = new UserProfile(LOGIN, passwordEncoder.encode(PASSWORD), EMAIL);
        when(userService.getUserByLogin(eq(LOGIN))).thenReturn(userMock);

        final UserProfile login = new UserProfile(LOGIN, PASSWORD, EMAIL);
        final ResponseEntity<Message> loginResponse = restTemplate.postForEntity(AUTH_URL, login, Message.class);

        verify(userService).getUserByLogin(eq(LOGIN));
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertEquals(AUTHORIZED.getMessage(), loginResponse.getBody());


        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, loginResponse.getHeaders().get("Set-Cookie"));
        final HttpEntity<Void> request = new HttpEntity<>(requestHeaders);

        final ResponseEntity<Message> response = restTemplate.exchange(AUTH_URL, HttpMethod.DELETE, request, Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LOGGED_OUT.getMessage(), response.getBody());
    }
}