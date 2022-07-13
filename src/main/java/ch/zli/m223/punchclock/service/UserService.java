package ch.zli.m223.punchclock.service;

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
    public void createNewUser(User user) {
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword())); //Hash the users password
        entityManager.persist(user);
    }
}
