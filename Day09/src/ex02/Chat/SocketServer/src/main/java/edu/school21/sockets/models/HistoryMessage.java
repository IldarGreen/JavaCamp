package edu.school21.sockets.models;

public class HistoryMessage {
    private String username;
    private String messageText;

    public HistoryMessage(String username, String messageText) {
        this.username = username;
        this.messageText = messageText;
    }

    public String getUsername() {
        return username;
    }

    public String getMessageText() {
        return messageText;
    }
}
