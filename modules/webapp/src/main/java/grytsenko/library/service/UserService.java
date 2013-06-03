package grytsenko.library.service;

import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
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

    /**
     * Creates and initializes a service.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds a user by its name.
     * 
     * @param name
     *            the name of user.
     * 
     * @return found user.
     */
    public User get(String name) {
        User user = userRepository.findByName(name);
        if (user != null) {
            LOGGER.debug("User {} was found.", name);
            return user;
        }

        user = new User();
        user.setName(name);
        user.setRole(UserRole.USER);
        user = userRepository.saveAndFlush(user);

        LOGGER.debug("User {} was added.", name);
        return user;
    }

}
