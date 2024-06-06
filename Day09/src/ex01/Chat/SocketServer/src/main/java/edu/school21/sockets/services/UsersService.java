package edu.school21.sockets.services;


import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;


public interface UsersService {
    boolean signUp(User user);
    boolean signIn(String username, String password);
    void saveMessage(User user, String message);
    User getSignInUser(String username, String password);
}
