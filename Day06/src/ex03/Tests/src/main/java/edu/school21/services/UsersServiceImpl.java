package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    boolean authenticate(String login, String password) throws AlreadyAuthenticatedException {
        User user = usersRepository.findByLogin(login);
        if (user.isAuthenticationSuccessStatus()) {
            throw new AlreadyAuthenticatedException("User with this login is already authorized");
        }
        if (user.getPassword().equals(password)) {
            user.setAuthenticationSuccessStatus(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }

}
