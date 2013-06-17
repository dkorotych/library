package grytsenko.library.service;

import grytsenko.library.model.DsUser;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
import grytsenko.library.repository.DsUsersRepository;
import grytsenko.library.repository.UsersRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manages users of library.
 */
@Service
public class ManageUsersService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ManageUsersService.class);

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DsUsersRepository dsUsersRepository;

    /**
     * Finds a user by its name.
     * 
     * <p>
     * If user is not found, then new user will be created.
     */
    public User get(String username) {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            LOGGER.debug("User {} is created.", username);
            user = createNewUser(username);
        }

        syncUserWithDs(user);

        return usersRepository.saveAndFlush(user);
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
     * Searches for a user in DS and then synchronizes it.
     */
    private void syncUserWithDs(User user) {
        String username = user.getUsername();
        DsUser dsUser = dsUsersRepository.findByUsername(username);

        if (dsUser == null) {
            LOGGER.debug("User {} was not found in DS.", username);
            return;
        }

        user.syncWith(dsUser);

        LOGGER.debug("User {} was synced with DS.", username);
    }

}
