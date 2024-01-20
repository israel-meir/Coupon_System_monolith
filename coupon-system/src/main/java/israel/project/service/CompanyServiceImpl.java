package israel.project.service;

import israel.project.data.dto.CouponDto;
import israel.project.data.dto.CouponDtoOnly;
import israel.project.data.entity.Company;
import israel.project.data.entity.Coupon;
import israel.project.data.repository.CompanyRepository;
import israel.project.data.repository.CouponRepository;
import israel.project.mapper.Mapper;
import israel.project.service.ex.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CouponRepository couponRepository;
    private final CompanyRepository companyRepository;
    private final Mapper mapper;

    @Override
    public CouponDto creation(UUID companyUuid, CouponDtoOnly couponDto) {
        if (couponDto.getAmount() > 1 || couponDto.getPrice() > 1) {
            throw new IllegalArgumentException("The price or quantity is less than 1");
        }

        if (couponDto.getStartDate().compareTo(Date.valueOf(LocalDate.now())) < 0 ||
                couponDto.getEndDate().compareTo(couponDto.getStartDate()) < 0) {
            throw new UnlikelyDateException("The start or end date doesn't make sense");
        }

        Optional<Company> optCompany = companyRepository.getCompanyByUuid(companyUuid);
        Optional<Coupon> optCoupon = couponRepository.getCouponByUuid(couponDto.getUuid());

        if (optCompany.isEmpty()) {
            throw new NoSuchCompanyException("The company is not found");
        }

        Company company = optCompany.get();

        if (optCoupon.isPresent() && company.getCoupons().stream()
                .anyMatch(companyCoupon -> companyCoupon.getUuid().equals(optCoupon.get().getUuid()))) {
            throw new TheCouponAlreadyExistsException("The coupon already exists in the system");
        }

        Coupon coupon = mapper.toCouponOnly(couponDto);
        coupon.setCompany(company);
        return mapper.toCouponDto(couponRepository.save(coupon));
    }

    @Override
    @Transactional
    public void deletion(UUID couponUuid, UUID companyUuid) {
        Optional<Company> optCompany = companyRepository.getCompanyByUuid(companyUuid);
        Optional<Coupon> optCoupon = couponRepository.getCouponByUuid(couponUuid);

        if (optCompany.isEmpty()) {
            throw new NoSuchCompanyException("Unauthorized access");
        }

        if (optCoupon.isEmpty()) {
            throw new NoSuchCouponException("The coupon is not found");
        }
        Coupon coupon = optCoupon.get();
        Company company = optCompany.get();

        if (company.getCoupons().stream().anyMatch(companyCoupon -> companyCoupon.getUuid().equals(coupon.getUuid()))) {
            couponRepository.delete(coupon);
        } else {
            throw new LimitedRemovalException("Unauthorized access");
        }
    }

    @Override
    public CouponDto quantityIncrease(UUID couponUuid, int amount, UUID companyUuid) {
        Optional<Coupon> optCoupon = couponRepository.getCouponByUuid(couponUuid);
        Optional<Company> optCompany = companyRepository.getCompanyByUuid(companyUuid);

        if (optCoupon.isEmpty()) {
            throw new NoSuchCouponException("The coupon is not found");
        }
        if (optCompany.isEmpty()) {
            throw new NoSuchCompanyException("Company not found");
        }

        if (amount < 1) {
            throw new IllegalArgumentException("Please enter a value greater than 0");
        }
        Company company = optCompany.get();
        Coupon coupon = optCoupon.get();

        if (company.getCoupons().stream().anyMatch(companyCoupon -> companyCoupon.getUuid().equals(coupon.getUuid()))) {
            coupon.setAmount(coupon.getAmount() + amount);
            return mapper.toCouponDto(couponRepository.save(coupon));
        } else {
            throw new LimitedRemovalException("Unauthorized access");
        }
    }

    @Override
    public Set<CouponDto> allCompanyCoupons(UUID companyUuid) {
        Optional<Company> optCompany = companyRepository.getCompanyByUuid(companyUuid);

        if (optCompany.isEmpty()) {
            throw new NoSuchCompanyException("the company is not found");
        }

        Company company = optCompany.get();

        if (company.getCoupons().isEmpty()) {
            throw new ThereAreNoCouponsException("There are no posted coupons");
        }

        return company.getCoupons().stream()
                .map(mapper::toCouponDto)
                .collect(Collectors.toSet());
    }
}
