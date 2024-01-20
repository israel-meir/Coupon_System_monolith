package israel.project.service;

import israel.project.data.dto.CouponDto;

import israel.project.data.entity.Coupon;
import israel.project.data.entity.Customer;
import israel.project.data.repository.CouponRepository;
import israel.project.data.repository.CustomerRepository;
import israel.project.mapper.Mapper;
import israel.project.service.ex.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final Mapper mapper;

    @Override
    public void purchase(UUID couponUuid, UUID customerUuid) {
        Optional<Customer> optCustomer = customerRepository.getByUuid(customerUuid);
        Optional<Coupon> optCoupon = couponRepository.getCouponByUuid(couponUuid);

        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException("The customer is not found");
        }
        if (optCoupon.isEmpty()) {
            throw new NoSuchCouponException("The coupon is not found");
        }

        Customer customer = optCustomer.get();
        Coupon coupon = optCoupon.get();

        if (coupon.getAmount() == 0) {
            throw new NoCouponsForSaleException("We apologize but the coupons are out of stock");
        }

        if (customer.getCoupons().stream()
                .anyMatch(existingCoupon -> Objects.equals(existingCoupon.getUuid(), coupon.getUuid()))) {
            throw new CouponAlreadyExistsException("The coupon is already in the cart");
        }

        customer.getCoupons().add(coupon);
        coupon.setAmount(coupon.getAmount() - 1);
        customerRepository.save(customer);
    }

    @Override
    public Set<CouponDto> allCouponsPurchased(UUID uuid) {
        Optional<Customer> customer = customerRepository.getByUuid(uuid);

        if (customer.isEmpty()) {
            throw new NoSuchCustomerException("The customer is not found");
        }

        return customer.get().getCoupons().stream()
                .map(mapper::toCouponDto).collect(Collectors.toSet());
    }

    @Override
    public Set<CouponDto> allUnclaimedCoupons(UUID customerUuid) {
        Optional<Customer> customer = customerRepository.getByUuid(customerUuid);
        Set<Coupon> allCoupons = couponRepository.findAll().stream().collect(Collectors.toSet());

        if (customer.isEmpty()) {
            throw new NoSuchCustomerException("The customer is not found");
        }

        if (allCoupons.isEmpty()) {
            throw new ThereAreNoCouponsException("There are no coupons");
        }

        Set<Coupon> customerCoupons = customer.get().getCoupons();

        if (customerCoupons != null) {
            allCoupons.removeIf(coupon -> customerCoupons.contains(coupon));
        }

        return allCoupons.stream()
                .map((Function<Coupon, CouponDto>) mapper::toCouponDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<CouponDto> expiresThisWeek(UUID uuid) {
        Optional<Customer> customer = customerRepository.getByUuid(uuid);

        if (customer.isEmpty()) {
            throw new NoSuchCustomerException("The customer is not found");
        }
        if (customer.get().getCoupons() == null) {
            throw new ThereAreNoCouponsException("There are no coupons");
        }

        Set<Coupon> coupons = customer.get().getCoupons();


        return coupons.stream()
                .filter(coupon -> {
                    long couponTime = coupon.getEndDate().getTime();
                    long currentTime = System.currentTimeMillis();
                    return couponTime - currentTime < TimeUnit.DAYS.toMillis(7);
                })
                .map(mapper::toCouponDto)
                .collect(Collectors.toSet());
    }
}
