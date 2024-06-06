package edu.school21.sockets.services;


import edu.school21.sockets.models.User;

import edu.school21.sockets.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private UsersRepositoryJdbcTemplateImpl usersRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepositoryJdbcTemplateImpl usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(User user) {
        if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username: " + user.getUsername() + " is already taken");
        }
        if (user.getPassword().isEmpty()) {
            throw new RuntimeException("Password not set");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
}
