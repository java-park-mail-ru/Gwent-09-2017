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
import ru.mail.park.gwent.domains.Message;
import ru.mail.park.gwent.domains.UserInfo;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ru.mail.park.gwent.domains.MessageEnum.AUTHORIZED;
import static ru.mail.park.gwent.domains.MessageEnum.NOT_AUTHORIZED;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SessionControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String AUTH_URL = "/api/auth";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email@email.ru";
    private static final String PASSWORD = "password";

    @Test
    public void testGetLoggedUserUnauthorized() {
        final ResponseEntity<Message> response = restTemplate.getForEntity(AUTH_URL, Message.class);

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
        final HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);

        final ResponseEntity<UserInfo> response = restTemplate.exchange(AUTH_URL, HttpMethod.GET, requestEntity, UserInfo.class);

        final UserInfo userInfo = response.getBody();

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(userInfo);
        assertEquals(LOGIN, userInfo.getLogin());
        assertEquals(EMAIL, userInfo.getEmail());
    }
}