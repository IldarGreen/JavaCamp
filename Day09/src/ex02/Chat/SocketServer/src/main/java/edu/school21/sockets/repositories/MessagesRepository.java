package edu.school21.sockets.repositories;

import edu.school21.sockets.models.HistoryMessage;
import edu.school21.sockets.models.Message;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message>{
    Message findLastRoomByAuthor(String author);
    List<HistoryMessage> find30MessagesByChatroomName(String chatroomName);
}
