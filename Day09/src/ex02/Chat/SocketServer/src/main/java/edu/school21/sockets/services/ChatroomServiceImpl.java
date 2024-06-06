package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.repositories.ChatroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChatroomServiceImpl implements ChatroomService {
    private ChatroomRepository chatroomRepository;

    @Autowired
    public ChatroomServiceImpl(ChatroomRepository chatroomRepository) {
        this.chatroomRepository = chatroomRepository;
    }

    @Override
    public void createChatroom(Chatroom chatroom) {
        if (chatroomRepository.findByChatroomName(chatroom.getChatroomname()).isPresent()) {
            throw new RuntimeException("Chatroom Name: " + chatroom.getChatroomname() + " is already taken");
        }
        chatroomRepository.save(chatroom);
    }

    @Override
    public List<Chatroom> getAllRooms() {
        return chatroomRepository.findAll();
    }

    @Override
    public Chatroom getChatroomByName(String chatroomName) {
        Optional<Chatroom> optional = chatroomRepository.findByChatroomName(chatroomName);
        return optional.orElse(null);
    }

    @Override
    public Chatroom getLastVisitedChatroom(String username) {
        Optional<Chatroom> optional = chatroomRepository.findLastVisitedChatroom(username);
        return optional.orElse(null);
    }

}
