package ch.zli.m223.punchclock.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.util.SecurityUtil;

@RequestScoped
public class AuthenticationService {
    
    @Inject
    private EntityManager entityManager;

    /**
     * Checks if the credentials of a given user are valid
     * Source: Moodle Block 4 Autorisierung und Authentifizerung, Lösungsideen: Auth & Category
     * @param user The User Object
     * @return Are the credentials valid
     */
    public boolean checkUserCredentials(User user){
        try {
            var query = entityManager.createQuery("FROM User WHERE username = :name", User.class);        
            query.setParameter("name", user.getUsername());
            User result = query.getSingleResult();
              //Check if the user with that username exists and if the password is correct
              return result != null && SecurityUtil.verifyPassword(user.getPassword(), result.getPassword());
        }catch(Exception e) {
            return false; //If there is any exception either with the verifying of the password or the query return unauthorized
        }
    }



    public String GenerateValidJwtToken(User user){
        String token =
            Jwt.issuer("https://zli.ch/issuer") 
            .upn(user.getUsername()) 
            .groups(new HashSet<>(Arrays.asList(user.getRole()))) 
            .claim(Claims.birthdate.name(), "2001-07-13")
            .expiresIn(Duration.ofHours(1)) 
            .sign();
        return token;
    }

}
