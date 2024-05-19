package edu.school21.services;


import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class UsersServiceImplTest {
    @Mock
    UsersRepository usersRepositoryMock;

    UsersServiceImpl usersService;

    public UsersServiceImplTest() {
        usersRepositoryMock = Mockito.mock(UsersRepository.class);
        this.usersService = new UsersServiceImpl(usersRepositoryMock);
    }

    @Test
    void correctLoginPasswordTest() {
        User user = new User(1L, "Bobby", "123", false);
        Mockito.when(usersRepositoryMock.findByLogin("Bobby")).thenReturn(user);
        assertTrue(usersService.authenticate("Bobby", "123"));
    }

    @Test
    void alreadyLogIn() {
        User user = new User(1L, "Bobby", "123", true);
        Mockito.when(usersRepositoryMock.findByLogin("Bobby")).thenReturn(user);
        assertThrowsExactly(AlreadyAuthenticatedException.class, () -> usersService.authenticate("Bobby", "123"));
    }

    @Test
    void incorrectLoginTest() {
        Mockito.when(usersRepositoryMock.findByLogin("NotABobby")).thenThrow(EntityNotFoundException.class);
        assertThrowsExactly(EntityNotFoundException.class, () -> usersService.authenticate("NotABobby", "123"));
    }

    @Test
    void incorrectPasswordTest() {
        User user = new User(1L, "Bobby", "123", false);
        Mockito.when(usersRepositoryMock.findByLogin("Bobby")).thenReturn(user);
        assertFalse(usersService.authenticate("Bobby", "000"));
    }

}
