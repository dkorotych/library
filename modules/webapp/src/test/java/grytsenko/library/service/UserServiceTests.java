package grytsenko.library.service;

import static grytsenko.library.test.MockitoUtils.doReturnFirstArgument;
import static grytsenko.library.test.TestUsers.GUEST_MAIL;
import static grytsenko.library.test.TestUsers.GUEST_NAME;
import static grytsenko.library.test.TestUsers.guest;
import static grytsenko.library.test.TestUsers.guestFromDs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
import grytsenko.library.repository.DsUserRepository;
import grytsenko.library.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link UserService}.
 */
public class UserServiceTests {

    UserRepository userRepository;

    UserService userService;
    DsUserRepository dsUserRepository;

    @Before
    public void prepare() throws Exception {
        // Setup data.
        userRepository = mock(UserRepository.class);
        dsUserRepository = mock(DsUserRepository.class);

        userService = new UserService(userRepository, dsUserRepository);

        // Setup behavior.
        doReturn(guestFromDs()).when(dsUserRepository)
                .findByUsername(anyString());
    }

    /**
     * User is already known.
     */
    @Test
    public void testGetKnownUser() throws Exception {
        // Setup data.
        User guest = guest();

        // Setup behavior.
        doReturn(guest).when(userRepository).findByUsername(anyString());
        doReturnFirstArgument().when(userRepository).save(any(User.class));

        // Execute.
        User user = userService.get(GUEST_NAME);

        // Verify data.
        assertSame(guest, user);
        assertEquals(GUEST_MAIL, user.getMail());

        // Verify behavior.
        verify(dsUserRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUserRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * User is created, because he accesses at first time.
     */
    @Test
    public void testGetUnknownUser() throws Exception {
        // Setup behavior.
        doReturn(null).when(userRepository).findByUsername(anyString());
        doReturnFirstArgument().when(userRepository).save(any(User.class));

        // Execute.
        User user = userService.get(GUEST_NAME);

        // Verify state.
        assertNotNull(user);
        assertEquals(GUEST_NAME, user.getUsername());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals(GUEST_MAIL, user.getMail());

        // Verify behavior.
        verify(dsUserRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUserRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * User was not updated, because it was not found in DS.
     */
    @Test
    public void testNotUpdatedFromLdap() throws Exception {
        // Setup behavior.
        doReturn(null).when(userRepository).findByUsername(anyString());
        doReturn(null).when(dsUserRepository).findByUsername(anyString());
        doReturnFirstArgument().when(userRepository).save(any(User.class));

        // Execute.
        User user = userService.get(GUEST_NAME);

        // Verify state.
        assertNotNull(user);
        assertEquals(GUEST_NAME, user.getUsername());
        assertEquals(UserRole.USER, user.getRole());
        assertNull(user.getMail());

        // Verify behavior.
        verify(dsUserRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUserRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

}
