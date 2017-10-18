package ru.mail.park.gwent.services.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.gwent.domains.UserProfile;
import ru.mail.park.gwent.services.UserService;
import ru.mail.park.gwent.services.exceptions.DuplicateUserException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

@Service
@Transactional
public class UserDao implements UserService {

    private final EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public void createUser(UserProfile userProfile) {
        try {
            em.persist(userProfile);
        } catch (PersistenceException ex) {
            throw new DuplicateUserException(userProfile.getLogin(), ex);
        }
    }

    @Override
    public void deleteUser(String login) {
        final UserProfile user = getUserByLogin(login);
        em.remove(user);
    }

    @Override
    public UserProfile getUserByLogin(String login) {
        return em.find(UserProfile.class, login);
    }

    @Override
    public void updateUser(String login, UserProfile newProfile) {
        em.merge(newProfile);
    }
}
