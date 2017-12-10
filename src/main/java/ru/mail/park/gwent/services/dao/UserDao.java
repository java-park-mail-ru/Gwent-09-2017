package ru.mail.park.gwent.services.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.gwent.domains.auth.UserInfo;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDao implements UserService {

    private final EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public UserProfile createUser(UserProfile profile) {
        try {
            em.persist(profile);
        } catch (PersistenceException ex) {
            throw new DuplicateUserException(profile.getLogin(), ex);
        }
        return profile;
    }

    @Override
    public void deleteUser(UserProfile profile) {
        em.remove(profile);
    }

    @Override
    public UserProfile getUserProfile(String login) {
        return em.find(UserProfile.class, login);
    }

    @Override
    public UserProfile updateUser(UserProfile profile) {
        return em.merge(profile);
    }

    @Override
    public boolean isExist(UserProfile profile) {
        final UserProfile user = getUserProfile(profile.getLogin());

        return user != null;
    }

    @Override
    public UserInfo getUserInfo(String login) {
        final UserProfile profile = getUserProfile(login);
        final List<UserProfile> profiles =
                em.createQuery("SELECT p FROM UserProfile p ORDER BY p.wins DESC, p.login", UserProfile.class)
                        .getResultList();

        final int position = profiles.indexOf(profile) + 1;

        return new UserInfo(profile, position);
    }

    @Override
    public List<UserInfo> getUsers(int limit, int offset) {
        final List<UserProfile> profiles =
                em.createQuery("SELECT p FROM UserProfile p ORDER BY p.wins DESC, p.login", UserProfile.class)
                        .setMaxResults(limit)
                        .setFirstResult(offset - 1)
                        .getResultList();

        final List<UserInfo> result = new ArrayList<>();
        for (int i = 0; i < profiles.size(); i++) {
            final int position = offset + i;
            final UserInfo userInfo = new UserInfo(profiles.get(i), position);
            result.add(userInfo);
        }

        return result;
    }
}
