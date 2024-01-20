package israel.project.service;

import israel.project.data.entity.Company;
import israel.project.data.entity.Customer;
import israel.project.data.repository.CompanyRepository;
import israel.project.data.repository.CustomerRepository;
import israel.project.login.ClientSession;
import israel.project.service.ex.NoSuchCompanyException;
import israel.project.service.ex.NoSuchCustomerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class LoginServiceImpl implements LoginService {
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    public static final int TOKEN_LENGTH = 10;
    private final int tokenExpirationMinutes;

    public LoginServiceImpl(CompanyRepository companyRepository,
                            CustomerRepository customerRepository,
                            @Value("${token.expiration.minuts}") int tokenExpirationMinutes) {

        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.tokenExpirationMinutes = tokenExpirationMinutes;
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "")
                .substring(0, TOKEN_LENGTH);
    }

    @Override
    public ClientSession createCompanySession(String email, String password) {
        Optional<Company> optCompany = companyRepository.getCompanyByEmailAndPassword(email, password);

        UUID companyUuid = optCompany
                .orElseThrow((Supplier<RuntimeException>)
                        () -> new NoSuchCompanyException("The company could not be found"))
                .getUuid();

        return ClientSession.creation(companyUuid, tokenExpirationMinutes);
    }

    @Override
    public ClientSession createCustomerSession(String email, String password) {
        Optional<Customer> optCustomer = customerRepository.getCustomersByEmailAndPassword(email, password);

        UUID customerUuid = optCustomer
                .orElseThrow((Supplier<RuntimeException>)
                        () -> new NoSuchCustomerException("The client is not found"))
                .getUuid();

        return ClientSession.creation(customerUuid, tokenExpirationMinutes);
    }

}
