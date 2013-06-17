package grytsenko.library.service;

import static grytsenko.library.test.MockitoUtils.doReturnFirstArgument;
import static grytsenko.library.test.Users.GUEST_MAIL;
import static grytsenko.library.test.Users.GUEST_NAME;
import static grytsenko.library.test.Users.guest;
import static grytsenko.library.test.Users.guestFromDs;
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
        userRepository = mock(UserRepository.class);
        dsUserRepository = mock(DsUserRepository.class);

        userService = new UserService(userRepository, dsUserRepository);

        doReturn(guestFromDs()).when(dsUserRepository).findByUsername(
                anyString());
    }

    @Test
    public void testGetKnownUser() throws Exception {
        User guest = guest();

        doReturn(guest).when(userRepository).findByUsername(anyString());
        doReturnFirstArgument().when(userRepository).saveAndFlush(
                any(User.class));

        User user = userService.get(GUEST_NAME);

        assertSame(guest, user);
        assertEquals(GUEST_MAIL, user.getMail());

        verify(dsUserRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUserRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).saveAndFlush(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetUnknownUser() throws Exception {
        doReturn(null).when(userRepository).findByUsername(anyString());
        doReturnFirstArgument().when(userRepository).saveAndFlush(
                any(User.class));

        User user = userService.get(GUEST_NAME);

        assertNotNull(user);
        assertEquals(GUEST_NAME, user.getUsername());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals(GUEST_MAIL, user.getMail());

        verify(dsUserRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUserRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).saveAndFlush(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testNotSyncedWithDs() throws Exception {
        doReturn(null).when(userRepository).findByUsername(anyString());
        doReturn(null).when(dsUserRepository).findByUsername(anyString());
        doReturnFirstArgument().when(userRepository).saveAndFlush(
                any(User.class));

        User user = userService.get(GUEST_NAME);

        assertNotNull(user);
        assertEquals(GUEST_NAME, user.getUsername());
        assertEquals(UserRole.USER, user.getRole());
        assertNull(user.getMail());

        verify(dsUserRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUserRepository);
        verify(userRepository).findByUsername(GUEST_NAME);
        verify(userRepository).saveAndFlush(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

}
