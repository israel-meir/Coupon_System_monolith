package israel.project.service;


import israel.project.data.dto.CouponDto;
import israel.project.data.dto.CouponDtoOnly;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public interface CompanyService {
    CouponDto creation(UUID companyUuid, CouponDtoOnly couponDto);

    void deletion(UUID couponUuid, UUID companyUuid);

    CouponDto quantityIncrease(UUID couponUuid, int amount, UUID companyUuid);

    Set<CouponDto> allCompanyCoupons(UUID companyUuid);
}
