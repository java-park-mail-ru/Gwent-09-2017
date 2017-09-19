package ru.mail.park.gwent.controllers.messages;

public class Message {
    private final String message;

    public Message(String message) {
        this.message = message;
    }

    @SuppressWarnings("unused")
    public String getMessage() {
        return message;
    }
}
