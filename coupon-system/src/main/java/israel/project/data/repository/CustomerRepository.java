package israel.project.data.repository;

import israel.project.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> getByUuid(UUID uuid);

    Optional<Customer> getCustomersByEmailAndPassword(String email, String password);
}
