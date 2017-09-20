package ru.mail.park.gwent.controllers.messages;

public enum MessageEnum {
    SIGNED_UP("User signed up"),
    NO_LOGIN_OR_PASSWORD("No login or password"),
    EMPTY_LOGIN_OR_PASSWORD("Empty login or password"),
    WRONG_LOGIN_OR_PASSWORD("Wrong login or password"),
    LOGIN_IS_ALREADY_TAKEN("Login is already taken"),
    AUTHORIZED("User authorized"),
    LOGGED_OUT("User logged out"),
    NOT_AUTHORIZED("User not authorized"),
    ALREADY_AUTHORIZED("User is already authorized"),
    ANOTHER_ALREADY_AUTHORIZED("Another user is already authorized, try to logout and login again"),
    USER_PROFILE_UPDATED ("User profile data updated");

    private Message message;

    MessageEnum(String message) {
        this.message = new Message(message);
    }

    public Message getMessage() {
        return message;
    }
}
