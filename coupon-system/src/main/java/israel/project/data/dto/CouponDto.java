package israel.project.data.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CouponDto {
    private Long id;
    private UUID uuid;
    private int category;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String title;
    private String description;
    private String image;
    private CompanyDto companyDto;
}
