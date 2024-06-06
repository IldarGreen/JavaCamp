package edu.school21.sockets.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private Long sender_id;
    private String messageText;
    private LocalDateTime sendingTime;

    public Message(Long sender_id, String messageText) {
        this.sender_id = sender_id;
        this.messageText = messageText;
        this.sendingTime = LocalDateTime.now();
    }

    public Message(Long sender_id, String messageText, LocalDateTime sendingTime) {
        this.sender_id = sender_id;
        this.messageText = messageText;
        this.sendingTime = sendingTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getSender_id() {
        return sender_id;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(LocalDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && Objects.equals(sender_id, message.sender_id) && Objects.equals(messageText, message.messageText) && Objects.equals(sendingTime, message.sendingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender_id, messageText, sendingTime);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender_id=" + sender_id +
                ", messageText='" + messageText + '\'' +
                ", sendingTime=" + sendingTime +
                '}';
    }
}