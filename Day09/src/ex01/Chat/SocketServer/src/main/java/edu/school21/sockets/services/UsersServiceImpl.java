package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import edu.school21.sockets.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private final UsersRepositoryJdbcTemplateImpl usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessagesRepositoryImpl messagesRepository;

    @Autowired
    public UsersServiceImpl(UsersRepositoryJdbcTemplateImpl usersRepository, PasswordEncoder passwordEncoder,
                            MessagesRepositoryImpl messagesRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public boolean signUp(User user) {

        boolean result = false;
        if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username: " + user.getUsername() + " is already taken");
        }
        if (user.getPassword().isEmpty()) {
            throw new RuntimeException("Password not set");
        }
        result = true;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return result;
    }

    @Override
    public boolean signIn(String username, String password) {
        Optional<User> optional = usersRepository.findByUsername(username);
        return optional.isPresent() && passwordEncoder.matches(password, optional.get().getPassword());
    }

    @Override
    public User getSignInUser(String username, String password) {
        Optional<User> optional = usersRepository.findByUsername(username);
        if (optional.isPresent() && passwordEncoder.matches(password, optional.get().getPassword())) {
            return optional.get();
        }
        return null;
    }

    @Override
    public void saveMessage(User user, String message) {
        messagesRepository.save(new Message(user.getId(), message));
    }

}
