package israel.project.data.dto;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CustomerDto {
    private UUID uuid;
    private String name;
    private String email;
    private String password;
    private Set<CouponDto> coupons;
}
