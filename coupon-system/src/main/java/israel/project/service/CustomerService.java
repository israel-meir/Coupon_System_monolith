package israel.project.service;

import israel.project.data.dto.CouponDto;
import israel.project.data.dto.CustomerDto;

import java.util.Set;
import java.util.UUID;

public interface CustomerService {
    void purchase(UUID couponUuid, UUID customerUuid);

    Set<CouponDto> allCouponsPurchased(UUID uuid);

    Set<CouponDto> allUnclaimedCoupons(UUID customerUuid);

    Set<CouponDto> expiresThisWeek(UUID uuid);

}
