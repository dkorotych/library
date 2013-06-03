package grytsenko.library.service;

import static grytsenko.library.test.MockitoUtils.doReturnFirstArgument;
import static grytsenko.library.test.TestUsers.GUEST_NAME;
import static grytsenko.library.test.TestUsers.guest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import grytsenko.library.model.User;
import grytsenko.library.model.UserRole;
import grytsenko.library.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link UserService}.
 */
public class UserServiceTests {

    UserRepository userRepository;

    UserService userService;

    @Before
    public void prepare() {
        userRepository = mock(UserRepository.class);

        userService = new UserService(userRepository);
    }

    /**
     * User is known, i.e. it should be taken from a storage.
     */
    @Test
    public void testGetKnownUser() throws Exception {
        // Setup data.
        User guest = guest();

        // Setup behavior.
        doReturn(guest).when(userRepository).findByName(anyString());

        // Execute.
        User user = userService.get(GUEST_NAME);

        // Verify data.
        assertSame(guest, user);

        // Verify behavior.
        verify(userRepository).findByName(GUEST_NAME);
        verifyNoMoreInteractions(userRepository);
    }

    /**
     * User is unknown, so it should be added to a storage.
     */
    @Test
    public void testGetUnknownUser() throws Exception {
        // Setup behavior.
        doReturn(null).when(userRepository).findByName(anyString());
        doReturnFirstArgument().when(userRepository).saveAndFlush(
                any(User.class));

        // Execute.
        User user = userService.get(GUEST_NAME);

        // Verify state.
        assertNotNull(user);
        assertEquals(GUEST_NAME, user.getName());
        assertEquals(UserRole.USER, user.getRole());

        // Verify behavior.
        verify(userRepository).findByName(GUEST_NAME);
        verify(userRepository).saveAndFlush(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

}
