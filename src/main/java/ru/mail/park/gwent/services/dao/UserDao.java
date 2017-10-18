package ru.mail.park.gwent.services.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserDao implements UserService {
    private final Map<String, UserProfile> loginToProfile = new HashMap<>();

    @Override
    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    @Override
    public void createUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    @Override
    public void deleteUser(String login) {
        loginToProfile.remove(login);
    }

    @Override
    public void updateUser(String login, UserProfile newProfile) {
        loginToProfile.put(login, newProfile);
    }
}
