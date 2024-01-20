package israel.project.login;

import israel.project.login.ex.SessionExpiredException;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
public class ClientSession {
    private UUID clientUuid;
    private long lastAccessTime;
    private long sessionExpirationTime;

    private ClientSession(UUID clientUuid, long lastAccessTime, long sessionExpirationTime) {
        this.clientUuid = clientUuid;
        this.lastAccessTime = lastAccessTime;
        this.sessionExpirationTime = sessionExpirationTime;
    }

    public static ClientSession creation(UUID uuid, long expirationTime) {
        return new ClientSession(uuid, System.currentTimeMillis(), expirationTime);
    }

    public void accessOrExpired() {
        if (expired()) {
            throw new SessionExpiredException("The connection has expired, please log in again");
        }
        lastAccessTime = System.currentTimeMillis();
    }

    private boolean expired() {
        long currentTime = System.currentTimeMillis();
        long sessionTime = TimeUnit.MILLISECONDS.toMinutes(currentTime - lastAccessTime);
        return sessionTime > sessionExpirationTime;
    }
}
