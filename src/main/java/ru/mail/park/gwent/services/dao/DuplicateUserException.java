package ru.mail.park.gwent.services.dao;

class DuplicateUserException extends RuntimeException {
    DuplicateUserException(String login, Throwable cause) {
        super("User with login " + login + " already exists", cause);
    }
}
