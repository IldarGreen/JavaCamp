package edu.school21.sockets.services;


import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.HistoryMessage;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.HistoryMessageMapper;

import java.util.List;


public interface UsersService {
    boolean signUp(User user);
    boolean logIn(String username, String password);
    void saveMessage(User user, Chatroom chatroom, String message);
    User getLogUpUser(String username, String password);
    User getLogUpUserOnlyName(String username);

    List<HistoryMessage> getFind30MessagesByChatroomName(String chatroomname);
}
