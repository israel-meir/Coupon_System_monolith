package israel.project.mapper;

import israel.project.data.dto.CompanyDto;
import israel.project.data.dto.CouponDto;
import israel.project.data.dto.CouponDtoOnly;
import israel.project.data.dto.CustomerDto;
import israel.project.data.entity.Company;
import israel.project.data.entity.Coupon;
import israel.project.data.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class mapperImpl implements Mapper {
    @Override
    public Company toCompany(CompanyDto dto) {
        return Company.builder()
                .uuid(dto.getUuid())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    @Override
    public CompanyDto toCompanyDto(Company company) {
        return CompanyDto.builder()
                .uuid(company.getUuid())
                .name(company.getName())
                .Email(company.getEmail())
                .build();
    }

    @Override
    public Customer toCustomer(CustomerDto dto) {
        return Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .uuid(dto.getUuid())
                .coupons(dto.getCoupons()
                        .stream()
                        .map(this::toCoupon)
                        .collect(Collectors.toSet())
                ).build();
    }

    @Override
    public CustomerDto toCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .uuid(customer.getUuid())
                .name(customer.getName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .coupons(customer.getCoupons().stream().map(this::toCouponDto).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Coupon toCoupon(CouponDto dto) {
        return Coupon.builder()
                .uuid(dto.getUuid())
                .category(dto.getCategory())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .image(dto.getImage())
                .company(toCompany(dto.getCompanyDto()))
                .build();
    }

    @Override
    public Coupon toCouponOnly(CouponDtoOnly dto) {
        return Coupon.builder()
                .uuid(dto.getUuid())
                .category(dto.getCategory())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .image(dto.getImage())
                .build();
    }

    @Override
    public CouponDto toCouponDto(Coupon coupon) {
        return CouponDto.builder()
                .id(coupon.getId())
                .uuid(coupon.getUuid())
                .category(coupon.getCategory())
                .startDate(coupon.getStartDate())
                .endDate(coupon.getEndDate())
                .amount(coupon.getAmount())
                .price(coupon.getPrice())
                .title(coupon.getTitle())
                .description(coupon.getDescription())
                .image(coupon.getImage())
                .companyDto(toCompanyDto(coupon.getCompany()))
                .build();
    }
}
