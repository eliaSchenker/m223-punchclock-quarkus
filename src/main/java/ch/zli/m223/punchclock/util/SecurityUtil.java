package ch.zli.m223.punchclock.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;

public class SecurityUtil {
    /**
     * Checks if a provided password matches a hashed password
     * Source: https://github.com/quarkusio/quarkus/issues/10855#issuecomment-736375209
     * @param originalPwd The entered password
     * @param encryptedPwd The hashed password (e.g. from the database)
     * @return Does the Password match
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static boolean verifyPassword(String originalPwd, String encryptedPwd) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException {
        // convert encrypted password string to a password key
        Password rawPassword = ModularCrypt.decode(encryptedPwd);

        // create the password factory based on the bcrypt algorithm
        PasswordFactory factory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT);

        // create encrypted password based on stored string
        BCryptPassword restored = (BCryptPassword) factory.translate(rawPassword);

        // verify restored password against original
        return factory.verify(restored, originalPwd.toCharArray());
}
}
