package israel.project.login;

import israel.project.controller.ex.TokenNotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanySessionManager {
    private final Map<String, ClientSession> companySessions;

    public UUID handleToken(String token) {
        ClientSession clientSession = companySessions.get(token);

        if (clientSession == null) {
            throw new TokenNotAuthorizedException("Company does not exist in the system, please try again");
        }

        clientSession.accessOrExpired();
        return clientSession.getClientUuid();
    }
}
