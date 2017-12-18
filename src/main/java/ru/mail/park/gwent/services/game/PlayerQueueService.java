package ru.mail.park.gwent.services.game;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import ru.mail.park.gwent.domains.auth.UserProfile;
import ru.mail.park.gwent.domains.game.UserPair;
import ru.mail.park.gwent.websocket.handler.HandleException;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class PlayerQueueService {
    private ConcurrentLinkedQueue<UserProfile> userQueue = new ConcurrentLinkedQueue<>();

    private UserSessionPointService remotePointService;

    public PlayerQueueService(UserSessionPointService remotePointService) {
        this.remotePointService = remotePointService;
    }

    @Nullable
    public UserPair joinUser(UserProfile user) throws HandleException {
        WebSocketSession session = remotePointService.getSession(user);
        if (!session.isOpen()) {
            throw new HandleException("Can't pair user with close session");
        }

        if (userQueue.contains(user)) {
            throw new HandleException("User already in queue");
        }

        if (userQueue.isEmpty()) {
            userQueue.add(user);
            return null;
        }

        UserProfile waitingUser = userQueue.poll();
        WebSocketSession waitingUserSession = remotePointService.getSession(waitingUser);
        while ((waitingUser == null || !waitingUserSession.isOpen()) && !userQueue.isEmpty()) {
            waitingUser = userQueue.poll();
        }

        if (waitingUser == null && userQueue.isEmpty()) {
            userQueue.add(user);
            return null;
        }

        return new UserPair(user, waitingUser);
    }
}
