package israel.project.mapper;

import israel.project.data.dto.CompanyDto;
import israel.project.data.dto.CouponDto;
import israel.project.data.dto.CouponDtoOnly;
import israel.project.data.dto.CustomerDto;
import israel.project.data.entity.Company;
import israel.project.data.entity.Coupon;
import israel.project.data.entity.Customer;

public interface Mapper {
    Company toCompany(CompanyDto dto);

    CompanyDto toCompanyDto(Company company);

    Customer toCustomer(CustomerDto dto);

    CustomerDto toCustomerDto(Customer customer);

    Coupon toCoupon(CouponDto dto);

    Coupon toCouponOnly(CouponDtoOnly dto);

    CouponDto toCouponDto(Coupon coupon);
}
