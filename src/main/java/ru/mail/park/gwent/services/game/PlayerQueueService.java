package ru.mail.park.gwent.services.game;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.mail.park.gwent.domains.game.UserPair;
import ru.mail.park.gwent.domains.game.WebSocketUser;
import ru.mail.park.gwent.websocket.handler.HandleException;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class PlayerQueueService {
    private ConcurrentLinkedQueue<WebSocketUser> userQueue = new ConcurrentLinkedQueue<>();

    @Nullable
    public UserPair joinUser(WebSocketUser user) throws HandleException {
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

        return new UserPair(user, waitingUser);
    }
}
