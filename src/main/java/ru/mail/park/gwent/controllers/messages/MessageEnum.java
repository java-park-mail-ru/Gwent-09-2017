package ru.mail.park.gwent.controllers.messages;

public enum MessageEnum {
    SIGNED_UP_MSG {
        @Override
        public Message getMessage() {
            return new Message("User signed up");
        }
    },
    NO_LOGIN_OR_PASSWORD_MSG {
        @Override
        public Message getMessage() {
            return new Message("No login or password");
        }
    },
    EMPTY_LOGIN_OR_PASSWORD_MSG {
        @Override
        public Message getMessage() {
            return new Message("Empty login or password");
        }
    },
    WRONG_LOGIN_OR_PASSWORD_MSG {
        @Override
        public Message getMessage() {
            return new Message("Wrong login or password");
        }
    },
    LOGIN_IS_ALREADY_TAKEN_MSG {
        @Override
        public Message getMessage() {
            return new Message("Login is already taken");
        }
    },
    AUTHORIZED_MSG {
        @Override
        public Message getMessage() {
            return new Message("User authorized");
        }
    },
    LOGGED_OUT_MSG {
        @Override
        public Message getMessage() {
            return new Message("User logged out");
        }
    },
    NOT_AUTHORIZED_MSG {
        @Override
        public Message getMessage() {
            return new Message("User not authorized");
        }
    },
    ALREADY_AUTHORIZED_MSG {
        @Override
        public Message getMessage() {
            return new Message("User is already authorized");
        }
    },
    ANOTHER_ALREADY_AUTHORIZED_MSG {
        @Override
        public Message getMessage() {
            return new Message("Another user is already authorized, try to logout and login again");
        }
    };

    @SuppressWarnings("unused")
    public abstract Message getMessage();
}
