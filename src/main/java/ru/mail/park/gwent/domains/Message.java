package ru.mail.park.gwent.domains;

import com.fasterxml.jackson.annotation.*;

public class Message {
    @JsonProperty("message")
    private String message;

    @JsonCreator
    public Message(String message) {
        this.message = message;
    }

    @JsonGetter
    public String getMessage() {
        return message;
    }

    @JsonSetter
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Message messageObj = (Message) obj;

        return message.equals(messageObj.message);
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}
