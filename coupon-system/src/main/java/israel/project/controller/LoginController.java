package israel.project.controller;

import israel.project.login.ClientSession;
import israel.project.login.LoginCredentials;
import israel.project.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("api/login")
@RequiredArgsConstructor
public class LoginController {
    public static final int COMPANY = 1;
    private final LoginService loginService;
    private final Map<String, ClientSession> companySessions;
    private final Map<String, ClientSession> customerSessions;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginCredentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        int clientType = credentials.getClientType();

        if (email == null || password == null || clientType > 1 || clientType < 0) {
            throw new IllegalArgumentException("The login information is incorrect." +
                    "Please enter email, password, and applicant type (company = 1, customer = 0)");
        }

        String token = loginService.generateToken();

        if (clientType == COMPANY) {
            ClientSession companySession = loginService.createCompanySession(email, password);
            companySessions.put(token, companySession);
        } else {
            ClientSession customerSession = loginService.createCustomerSession(email, password);
            customerSessions.put(token, customerSession);
        }

        return ResponseEntity.ok(token);
    }
}
