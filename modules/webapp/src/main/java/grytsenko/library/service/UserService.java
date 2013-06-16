package grytsenko.library.service;

import grytsenko.library.model.DsUser;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
import grytsenko.library.repository.DsUserRepository;
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
    private DsUserRepository dsUserRepository;

    /**
     * Creates and initializes a service.
     */
    @Autowired
    public UserService(UserRepository userRepository,
            DsUserRepository dsUserRepository) {
        this.userRepository = userRepository;
        this.dsUserRepository = dsUserRepository;
    }

    /**
     * Finds a user by its name. If user is not found, then new user will be
     * created.
     */
    public User get(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.debug("User {} is created.", username);
            user = createNewUser(username);
        }

        updateUserFromDs(user);

        return userRepository.save(user);
    }

    /**
     * Creates the new user with the given name.
     */
    private User createNewUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setRole(UserRole.USER);
        return user;
    }

    /**
     * Searches for a user in directory service and then updates it.
     */
    private void updateUserFromDs(User user) {
        String username = user.getUsername();
        DsUser dsUser = dsUserRepository.findByUsername(username);

        if (dsUser == null) {
            LOGGER.debug("User {} was not updated.", username);
            return;
        }

        user.setFirstname(dsUser.getFirstname());
        user.setLastname(dsUser.getLastname());
        user.setMail(dsUser.getMail());

        LOGGER.debug("User {} was updated using data from DS.", username);
    }

}
