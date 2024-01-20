package israel.project.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginCredentials {
    private String email;
    private String password;
    private int clientType;
}
