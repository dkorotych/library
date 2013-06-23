package grytsenko.library.context;

import grytsenko.library.model.User;
import grytsenko.library.service.ManageUsersService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;

public class UpdateUser implements
        ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UpdateUser.class);

    @Autowired
    ManageUsersService manageUsersService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName();
        LOGGER.debug("User {} is authenticated.", username);

        User user = manageUsersService.find(username);
        if (user == null) {
            LOGGER.debug("User {} not found.", username);
            user = ManageUsersService.createNew(username);
        }

        manageUsersService.syncWithDs(user);
        manageUsersService.update(user);
        LOGGER.debug("User {} was updated.", username);
    }

}
