package ru.mail.park.gwent.websocket.handler;

class HandleException extends Exception {
    HandleException(String message, Throwable cause) {
        super(message, cause);
    }

    HandleException(String message) {
        super(message);
    }
}
