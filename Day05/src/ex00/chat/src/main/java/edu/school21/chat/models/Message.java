package edu.school21.chat.models;

import java.sql.Date;
import java.util.Objects;

public class Message {
    private int id;
    private User author;
    private Chatroom chatroom;
    private String text;
    private Date datetime;

    public Message(int id, User author, Chatroom chatroom, String text, Date datetime) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && Objects.equals(author, message.author) && Objects.equals(chatroom, message.chatroom)
                && Objects.equals(text, message.text) && Objects.equals(datetime, message.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, chatroom, text, datetime);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author=" + author +
                ", chatroom=" + chatroom +
                ", text='" + text + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}

