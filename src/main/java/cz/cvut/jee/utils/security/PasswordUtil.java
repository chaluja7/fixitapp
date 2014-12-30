package cz.cvut.jee.utils.security;

import org.jboss.security.auth.spi.Util;

/**
 * Password util.
 *
 * @author jakubchalupa
 * @since 30.12.14
 */
public class PasswordUtil {

    public static String generateHash(String password) {
        return Util.createPasswordHash("SHA-256", "BASE64", null, null, password);
    }

}
