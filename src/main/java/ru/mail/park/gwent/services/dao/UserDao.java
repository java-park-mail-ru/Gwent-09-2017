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
    public UserProfile getUserByLogin(String login) {
        return em.find(UserProfile.class, login);
    }

    @Override
    public UserProfile updateUser(UserProfile profile) {
        return em.merge(profile);
    }

    @Override
    public boolean isExist(UserProfile profile) {
        return em.contains(profile);
    }
}
