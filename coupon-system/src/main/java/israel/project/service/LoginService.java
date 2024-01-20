package israel.project.service;

import israel.project.login.ClientSession;
import israel.project.login.LoginCredentials;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String generateToken();

    ClientSession createCompanySession(String email, String password);

    ClientSession createCustomerSession(String email, String password);
}
