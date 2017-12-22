package ru.mail.park.gwent.domains.auth;

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
    OFFSET_MUST_BE_OVER_THAN_ZERO("Offset must be over than zero"),
    USER_LIST_IS_EMPTY("The user list is empty. Try to specify the offset less than the current one.");

    private Message message;

    MessageEnum(String message) {
        this.message = new Message(message);
    }

    public Message getMessage() {
        return message;
    }
}
