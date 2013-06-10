package grytsenko.library.repository;

import grytsenko.library.model.LdapUser;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Repository;

/**
 * Provides access to information from LDAP.
 */
@Repository
public class LdapRepository {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LdapRepository.class);

    public static final String USERS = "ldap.users";
    public static final String USERS_FILTER = "ldap.users.filter";

    public static final String USER_USERNAME = "ldap.user.username";
    public static final String USER_FIRSTNAME = "ldap.user.firstname";
    public static final String USER_LASTNAME = "ldap.user.lastname";
    public static final String USER_MAIL = "ldap.user.mail";

    private LdapContextSource ldapContextSource;
    private Properties ldapProperties;

    /**
     * Creates and initializes a repository.
     */
    @Autowired
    public LdapRepository(LdapContextSource ldapContextSource,
            Properties ldapProperties) {
        this.ldapContextSource = ldapContextSource;
        this.ldapProperties = ldapProperties;
    }

    /**
     * Finds a user in LDAP.
     * 
     * <p>
     * If several users will be found for the given name, then the first user
     * from this set will be returned.
     * 
     * @param username
     *            the name of user.
     * 
     * @return the found user or <code>null</code> if user was not found.
     */
    public LdapUser findByUsername(String username) {
        LOGGER.debug("Search for the user {} in LDAP.", username);

        LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource);
        String base = ldapProperties.getProperty(USERS);
        String pattern = ldapProperties.getProperty(USERS_FILTER);
        String filter = MessageFormat.format(pattern, username);
        LOGGER.debug("Used base DN {} and filter {}.", base, filter);

        List<?> foundUsers = ldapTemplate.search(base, filter,
                new LdapUserMapper());
        if (foundUsers.isEmpty()) {
            LOGGER.error("User {} was not found in LDAP.", username);
            return null;
        }
        if (foundUsers.size() > 0) {
            LOGGER.warn("Several users with identifier {} were found.",
                    username);
        }

        LdapUser foundUser = (LdapUser) foundUsers.get(0);

        LOGGER.debug("User {} was found in LDAP.", foundUser.getUsername());
        return foundUser;
    }

    private class LdapUserMapper implements AttributesMapper {

        @Override
        public LdapUser mapFromAttributes(Attributes attributes)
                throws NamingException {
            LdapUser user = new LdapUser();

            String usernameId = ldapProperties.getProperty(USER_USERNAME);
            user.setUsername((String) attributes.get(usernameId).get());

            String firstnameId = ldapProperties.getProperty(USER_FIRSTNAME);
            Attribute firstnameAttr = attributes.get(firstnameId);
            if (firstnameAttr != null) {
                user.setFirstname((String) firstnameAttr.get());
            }

            String lastnameId = ldapProperties.getProperty(USER_LASTNAME);
            Attribute lastnameAttr = attributes.get(lastnameId);
            if (lastnameAttr != null) {
                user.setLastname((String) lastnameAttr.get());
            }

            String mailId = ldapProperties.getProperty(USER_MAIL);
            Attribute mailAttr = attributes.get(mailId);
            if (mailAttr != null) {
                user.setMail((String) mailAttr.get());
            }

            return user;
        }

    }

}
