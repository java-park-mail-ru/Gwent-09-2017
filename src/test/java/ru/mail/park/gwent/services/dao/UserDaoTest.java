package ru.mail.park.gwent.services.dao;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.services.UserService;

import static org.junit.Assert.*;

/**
 * @author Konstantin Gulyy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserDaoTest {
    private static final String LOGIN = "login";
    private static final String EMAIL = "email@email.ru";
    private static final String PASSWORD = "password";
    private static final String UPDATED_EMAIL = "updated@email.ru";
    private static final String UPDATED_PASSWORD = "updated_password";
    private static final String EXPECTED_MESSAGE = "User with login login already exists";

    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void testSimpleCreateUser() {
        final UserProfile newUser = new UserProfile(LOGIN, PASSWORD, EMAIL);

        final UserProfile createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals(LOGIN, createdUser.getLogin());
        assertEquals(PASSWORD, createdUser.getPassword());
        assertEquals(EMAIL, createdUser.getEmail());
    }

    @Test
    public void testCreateDuplicateUser() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);
        userService.createUser(user);

        expected.expect(DuplicateUserException.class);
        expected.expectMessage(EXPECTED_MESSAGE);

        final UserProfile duplicatedUser = new UserProfile(LOGIN, PASSWORD, EMAIL);
        userService.createUser(duplicatedUser);
    }

    @Test
    public void testDeleteUser() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);
        final UserProfile createdUser = userService.createUser(user);

        userService.deleteUser(createdUser);
        final UserProfile foundedUser = userService.getUserProfile(createdUser.getLogin());

        assertNull(foundedUser);
    }

    @Test
    public void testGetUserByLogin() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);
        final UserProfile createdUser = userService.createUser(user);

        final UserProfile foundedUser = userService.getUserProfile(createdUser.getLogin());

        assertNotNull(foundedUser);
    }

    @Test
    public void updateUser() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);
        userService.createUser(user);
        user.setPassword(UPDATED_PASSWORD);
        user.setEmail(UPDATED_EMAIL);

        final UserProfile updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals(LOGIN, updatedUser.getLogin());
        assertEquals(UPDATED_PASSWORD, updatedUser.getPassword());
        assertEquals(UPDATED_EMAIL, updatedUser.getEmail());
    }

    @Test
    public void isNotExistUser() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);

        final boolean isExist = userService.isExist(user);

        assertFalse(isExist);
    }

    @Test
    public void isExistUser() {
        final UserProfile user = new UserProfile(LOGIN, PASSWORD, EMAIL);
        userService.createUser(user);

        final boolean isExist = userService.isExist(user);

        assertTrue(isExist);
    }
}