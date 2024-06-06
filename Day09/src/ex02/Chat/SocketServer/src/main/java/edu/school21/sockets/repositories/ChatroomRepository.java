package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends CrudRepository<Chatroom>{
    Optional<Chatroom> findByChatroomName(String chatroomname);
    Optional<Chatroom> findLastVisitedChatroom(String username);
}
