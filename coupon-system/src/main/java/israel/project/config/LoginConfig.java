package israel.project.config;

import israel.project.login.ClientSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LoginConfig {

    @Bean
    public Map<String, ClientSession> customerSessions() {
        return new HashMap<>();
    }

    @Bean
    public Map<String, ClientSession> companySessions() {
        return new HashMap<>();
    }
}
