package ru.mail.park.gwent.services;

import ru.mail.park.gwent.domains.auth.UserInfo;
import ru.mail.park.gwent.domains.auth.UserProfile;

import java.util.List;

public interface UserService {
    UserProfile createUser(UserProfile profile);

    void deleteUser(UserProfile profile);

    UserProfile getUserProfile(String login);

    UserProfile updateUser(UserProfile profile);

    boolean isExist(UserProfile profile);

    UserInfo getUserInfo(String login);

    List<UserInfo> getUsers(int limit, int offset);
}
