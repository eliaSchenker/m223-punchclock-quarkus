package ch.zli.m223.punchclock.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.User;
import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class UserService {
    @Inject
    private EntityManager entityManager;

    @Transactional 
    public User getUserByUsername(String username) {
        var query = entityManager.createQuery("FROM User WHERE username = :name", User.class);        
        query.setParameter("name", username);
        return query.getSingleResult();
    }

    @Transactional
    public User createNewUser(User user) {
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword())); //Hash the users password
        entityManager.persist(user);
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        var query = entityManager.createQuery("FROM User");
        return query.getResultList();
    }

    @Transactional
    public User updateUser(User user) {
        //If the password was not set by the update, use the password of the existing user
        if(user.getPassword() == "" || user.getPassword() == null) {
            User toEditUser = entityManager.find(User.class, user.getId());
            user.setPassword(toEditUser.getPassword());
        }else {
            user.setPassword(BcryptUtil.bcryptHash(user.getPassword())); //Hash the users new password
        }
        entityManager.merge(user);
        return user;
    }

    @Transactional
    public User deleteUser(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        return user;
    }
}
