package ru.mail.park.gwent.controllers.messages;

public enum MessageEnum {
    SIGNED_UP {
        @Override
        public Message getMessage() {
            return new Message("User signed up");
        }
    },
    NO_LOGIN_OR_PASSWORD {
        @Override
        public Message getMessage() {
            return new Message("No login or password");
        }
    },
    EMPTY_LOGIN_OR_PASSWORD {
        @Override
        public Message getMessage() {
            return new Message("Empty login or password");
        }
    },
    WRONG_LOGIN_OR_PASSWORD {
        @Override
        public Message getMessage() {
            return new Message("Wrong login or password");
        }
    },
    LOGIN_IS_ALREADY_TAKEN {
        @Override
        public Message getMessage() {
            return new Message("Login is already taken");
        }
    },
    AUTHORIZED {
        @Override
        public Message getMessage() {
            return new Message("User authorized");
        }
    },
    LOGGED_OUT {
        @Override
        public Message getMessage() {
            return new Message("User logged out");
        }
    },
    NOT_AUTHORIZED {
        @Override
        public Message getMessage() {
            return new Message("User not authorized");
        }
    },
    ALREADY_AUTHORIZED {
        @Override
        public Message getMessage() {
            return new Message("User is already authorized");
        }
    },
    ANOTHER_ALREADY_AUTHORIZED {
        @Override
        public Message getMessage() {
            return new Message("Another user is already authorized, try to logout and login again");
        }
    },
    USER_PROFILE_UPDATED {
        @Override
        public Message getMessage() {
            return new Message("User profile data updated");
        }
    };

    public abstract Message getMessage();
}
