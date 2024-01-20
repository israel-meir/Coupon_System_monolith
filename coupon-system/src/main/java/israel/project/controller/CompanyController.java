package israel.project.controller;

import israel.project.data.dto.CouponDto;
import israel.project.data.dto.CouponDtoOnly;
import israel.project.login.CompanySessionManager;
import israel.project.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("api/company")
@ResponseBody
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CompanySessionManager companySessionManager;

    @PostMapping("/create-coupon/{token}")
    public ResponseEntity<CouponDto> creat(@PathVariable String token,
                                           @RequestBody CouponDtoOnly couponDto) {

        CouponDto newCoupon = companyService.creation(companySessionManager.handleToken(token), couponDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/fetch-coupon/{id}")
                .buildAndExpand(newCoupon.getUuid())
                .toUri();

        return ResponseEntity.created(uri).body(newCoupon);
    }


    @PostMapping("/update-amount/{token}")
    public ResponseEntity<CouponDto> updateAmount(@PathVariable String token,
                                                  @RequestParam UUID couponUuid,
                                                  @RequestParam int amount) {
        return ResponseEntity.ok(companyService.quantityIncrease(couponUuid, amount,companySessionManager.handleToken(token)));
    }

    @DeleteMapping("/{token}/delete-coupon")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam UUID couponUuid,
                       @PathVariable String token) {
        companyService.deletion(couponUuid, companySessionManager.handleToken(token));
    }

    @GetMapping("/all-published-coupons/{token}")
    public ResponseEntity<Set<CouponDto>> getAllCoupons(@PathVariable String token) {
        return ResponseEntity.ok(companyService.allCompanyCoupons(companySessionManager.handleToken(token)));
    }
}
