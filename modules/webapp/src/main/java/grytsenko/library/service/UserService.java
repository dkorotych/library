package grytsenko.library.service;

import grytsenko.library.model.LdapUser;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
import grytsenko.library.repository.LdapRepository;
import grytsenko.library.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manages users of library.
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserService.class);

    private UserRepository userRepository;
    private LdapRepository ldapRepository;

    /**
     * Creates and initializes a service.
     */
    @Autowired
    public UserService(UserRepository userRepository,
            LdapRepository ldapRepository) {
        this.userRepository = userRepository;
        this.ldapRepository = ldapRepository;
    }

    /**
     * Finds a user by its name. If user is not found, then new user will be
     * created.
     */
    public User get(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.debug("User {} was not found.", username);

            user = createUser(username);
        }

        updateUserFromLdap(user);

        return userRepository.save(user);
    }

    /**
     * Creates the default user with the given name.
     */
    private User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setRole(UserRole.USER);
        return user;
    }

    /**
     * Searches for a user in LDAP and then updates it using data from LDAP.
     */
    private void updateUserFromLdap(User user) {
        String username = user.getUsername();

        LdapUser ldapUser = ldapRepository.findByUsername(username);

        if (ldapUser == null) {
            LOGGER.debug("User {} was not updated.", username);
            return;
        }

        user.setFirstname(ldapUser.getFirstname());
        user.setLastname(ldapUser.getLastname());
        user.setMail(ldapUser.getMail());

        LOGGER.debug("User {} was updated using data from LDAP.", username);
    }

}
