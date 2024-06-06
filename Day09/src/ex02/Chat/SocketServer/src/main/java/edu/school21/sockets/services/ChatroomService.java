package edu.school21.sockets.services;

import java.util.List;

import edu.school21.sockets.models.Chatroom;

public interface ChatroomService {
    void createChatroom(Chatroom chatroom);
    List<Chatroom> getAllRooms();
    Chatroom getChatroomByName(String chatroomName);

    Chatroom getLastVisitedChatroom(String chatroomName);
}
