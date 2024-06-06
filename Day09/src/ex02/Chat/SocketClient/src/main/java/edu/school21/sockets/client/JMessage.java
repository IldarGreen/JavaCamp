package edu.school21.sockets.client;

public class JMessage {
    private String message;
    private String color;

    public JMessage() {
    }

    public JMessage(String message) {
        this.message = message;
    }

    public JMessage(String message, String color) {
        this.message = message;
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
