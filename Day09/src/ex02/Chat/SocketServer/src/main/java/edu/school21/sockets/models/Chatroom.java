package edu.school21.sockets.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long id;
    private Long creator_id;
    private String chatroomname;
    private List<Message> messages;

    public Chatroom(Long creator_id, String chatroomname) {
        this.creator_id = creator_id;
        this.chatroomname = chatroomname;
    }

    public Chatroom(Long id, Long creator_id, String chatroomname) {
        this.id = id;
        this.creator_id = creator_id;
        this.chatroomname = chatroomname;
    }

    public Chatroom(Long id, Long creator_id, String chatroomname, List<Message> messages) {
        this.id = id;
        this.creator_id = creator_id;
        this.chatroomname = chatroomname;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatroomname() {
        return chatroomname;
    }

    public void setChatroomname(String chatroomname) {
        this.chatroomname = chatroomname;
    }

    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return Objects.equals(id, chatroom.id) && Objects.equals(chatroomname, chatroom.chatroomname)
                && Objects.equals(creator_id, chatroom.creator_id) && Objects.equals(messages, chatroom.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatroomname, creator_id, messages);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", chatroomname='" + chatroomname + '\'' +
                ", creator_id=" + creator_id +
                ", messages=" + messages +
                '}';
    }
}
