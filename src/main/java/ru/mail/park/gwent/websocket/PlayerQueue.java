package ru.mail.park.gwent.websocket;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class PlayerQueue {
    private ConcurrentLinkedQueue<WebSocketUser> userQueue = new ConcurrentLinkedQueue<>();

    @Nullable
    public Lobby addUser(WebSocketUser user) throws HandleException {
        if (!user.getSession().isOpen()) {
            throw new HandleException("Can't pair user with close session");
        }

        if (userQueue.contains(user)) {
            throw new HandleException("User already in queue");
        }

        if (userQueue.isEmpty()) {
            userQueue.add(user);
            return null;
        }

        WebSocketUser waitingUser = userQueue.poll();
        while ((waitingUser == null || !waitingUser.getSession().isOpen()) && !userQueue.isEmpty()) {
            waitingUser = userQueue.poll();
        }

        if (waitingUser == null && userQueue.isEmpty()) {
            userQueue.add(user);
            return null;
        }

        return new Lobby(user, waitingUser);
    }
}
