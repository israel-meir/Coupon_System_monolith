package israel.project.data.repository;

import israel.project.data.entity.Company;
import israel.project.data.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> getCompanyByUuid(UUID uuid);

    Optional<Company> getCompanyByEmailAndPassword(String email, String password);


}
