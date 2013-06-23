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

    /**
     * Creates the new user with the given name.
     */
    public static User createNew(String username) {
        LOGGER.debug("Create new user {}.", username);

        User user = new User();
        user.setUsername(username);
        user.setRole(UserRole.USER);
        return user;
    }

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
    public User find(String username) {
        LOGGER.debug("Search user {}.", username);
        return usersRepository.findByUsername(username);
    }

    /**
     * Updates user.
     */
    public void update(User user) {
        LOGGER.debug("Update user {}.", user.getUsername());
        usersRepository.saveAndFlush(user);
    }

    /**
     * Searches for a user in DS and then synchronizes it.
     */
    public void syncWithDs(User user) {
        String username = user.getUsername();
        LOGGER.debug("Sync user {} with DS.", username);

        DsUser dsUser = dsUsersRepository.findByUsername(username);

        if (dsUser == null) {
            LOGGER.debug("User {} not found in DS.", username);
            return;
        }

        user.syncWith(dsUser);

        LOGGER.debug("User {} synced with DS.", username);
    }

}
