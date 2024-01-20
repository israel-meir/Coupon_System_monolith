package israel.project.login;

import israel.project.controller.ex.TokenNotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerSessionManager {
    private final Map<String, ClientSession> customerSessions;

    public UUID handleToken(String token) {
        ClientSession clientSession = customerSessions.get(token);

        if (clientSession == null) {
            throw new TokenNotAuthorizedException("The customer doesn't exist in the system, please try again");
        }

        clientSession.accessOrExpired();
        return clientSession.getClientUuid();
    }
}
