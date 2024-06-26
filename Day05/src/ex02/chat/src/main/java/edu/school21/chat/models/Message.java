package edu.school21.chat.models;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Message {
    private Long id;
    private User author;
    private Chatroom chatroom;
    private String text;
    private LocalDateTime localDateTime;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    public Message(Long id, User author, Chatroom chatroom, String text, LocalDateTime localDateTime) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && Objects.equals(author, message.author) && Objects.equals(chatroom, message.chatroom)
                && Objects.equals(text, message.text) && Objects.equals(localDateTime, message.getLocalDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, chatroom, text, localDateTime);
    }

    @Override
    public String toString() {
        return "Message : {" +
                "\n  id=" + id +
                ",\n  author=" + author +
                ",\n  room=" + chatroom +
                ",\n  text=\"" + text + '\"' +
                ",\n  dateTime=" + localDateTime.format(DTF) +
                "\n}";
    }
}
