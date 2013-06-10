package grytsenko.library.service;

import static grytsenko.library.test.MockitoUtils.doReturnFirstArgument;
import static grytsenko.library.test.TestUsers.GUEST_MAIL;
import static grytsenko.library.test.TestUsers.GUEST_NAME;
import static grytsenko.library.test.TestUsers.guest;
import static grytsenko.library.test.TestUsers.guestLdap;
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
import grytsenko.library.repository.LdapRepository;
import grytsenko.library.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link UserService}.
 */
public class UserServiceTests {

    UserRepository userRepository;

    UserService userService;
    LdapRepository ldapRepository;

    @Before
    public void prepare() throws Exception {
        // Setup data.
        userRepository = mock(UserRepository.class);
        ldapRepository = mock(LdapRepository.class);

        userService = new UserService(userRepository, ldapRepository);

        // Setup behavior.
        doReturn(guestLdap()).when(ldapRepository).findByUsername(anyString());
    }

    /**
     * User is known, i.e. it should be taken from a storage.
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
        verify(ldapRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(ldapRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * User is unknown, so it should be added to a storage.
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
        verify(ldapRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(ldapRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * User was not updated, because it was not found in LDAP.
     */
    @Test
    public void testNotUpdatedFromLdap() throws Exception {
        // Setup behavior.
        doReturn(null).when(userRepository).findByUsername(anyString());
        doReturn(null).when(ldapRepository).findByUsername(anyString());
        doReturnFirstArgument().when(userRepository).save(any(User.class));

        // Execute.
        User user = userService.get(GUEST_NAME);

        // Verify state.
        assertNotNull(user);
        assertEquals(GUEST_NAME, user.getUsername());
        assertEquals(UserRole.USER, user.getRole());
        assertNull(user.getMail());

        // Verify behavior.
        verify(ldapRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(ldapRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

}
