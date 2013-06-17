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
import grytsenko.library.repository.DsUsersRepository;
import grytsenko.library.repository.UsersRepository;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ManageUsersService}.
 */
public class ManageUsersServiceTests {

    UsersRepository usersRepository;
    DsUsersRepository dsUsersRepository;

    ManageUsersService manageUsersService;

    @Before
    public void prepare() throws Exception {
        usersRepository = mock(UsersRepository.class);
        dsUsersRepository = mock(DsUsersRepository.class);

        manageUsersService = new ManageUsersService();
        manageUsersService.usersRepository = usersRepository;
        manageUsersService.dsUsersRepository = dsUsersRepository;

        doReturn(guestFromDs()).when(dsUsersRepository).findByUsername(
                anyString());
    }

    @Test
    public void testGetKnownUser() throws Exception {
        User guest = guest();

        doReturn(guest).when(usersRepository).findByUsername(anyString());
        doReturnFirstArgument().when(usersRepository).saveAndFlush(
                any(User.class));

        User user = manageUsersService.get(GUEST_NAME);

        assertSame(guest, user);
        assertEquals(GUEST_MAIL, user.getMail());

        verify(dsUsersRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUsersRepository);
        verify(usersRepository).findByUsername(GUEST_NAME);
        verify(usersRepository).saveAndFlush(user);
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void testGetUnknownUser() throws Exception {
        doReturn(null).when(usersRepository).findByUsername(anyString());
        doReturnFirstArgument().when(usersRepository).saveAndFlush(
                any(User.class));

        User user = manageUsersService.get(GUEST_NAME);

        assertNotNull(user);
        assertEquals(GUEST_NAME, user.getUsername());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals(GUEST_MAIL, user.getMail());

        verify(dsUsersRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUsersRepository);
        verify(usersRepository).findByUsername(GUEST_NAME);
        verify(usersRepository).saveAndFlush(any(User.class));
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void testNotSyncedWithDs() throws Exception {
        doReturn(null).when(usersRepository).findByUsername(anyString());
        doReturn(null).when(dsUsersRepository).findByUsername(anyString());
        doReturnFirstArgument().when(usersRepository).saveAndFlush(
                any(User.class));

        User user = manageUsersService.get(GUEST_NAME);

        assertNotNull(user);
        assertEquals(GUEST_NAME, user.getUsername());
        assertEquals(UserRole.USER, user.getRole());
        assertNull(user.getMail());

        verify(dsUsersRepository).findByUsername(GUEST_NAME);
        verifyNoMoreInteractions(dsUsersRepository);
        verify(usersRepository).findByUsername(GUEST_NAME);
        verify(usersRepository).saveAndFlush(any(User.class));
        verifyNoMoreInteractions(usersRepository);
    }

}
