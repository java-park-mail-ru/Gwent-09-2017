package ru.mail.park.gwent.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    private String message;

    @JsonCreator
    public Message(@JsonProperty("message") String message) {
        this.message = message;
    }

    @JsonGetter
    public String getMessage() {
        return message;
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
