package israel.project.controller;

import israel.project.data.dto.CouponDto;
import israel.project.data.dto.CustomerDto;
import israel.project.login.CustomerSessionManager;
import israel.project.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("api/customer")
@ResponseBody
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerSessionManager customerSessionManager;

    @PostMapping("/purchase-coupon/{coupon-uuid}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void purchase(
            @PathVariable(name = "coupon-uuid") UUID couponUuid,
            @PathVariable(name = "token") String token) {
        customerService.purchase(couponUuid, customerSessionManager.handleToken(token));
    }

    @GetMapping("/all-my-coupons/{token}")
    public ResponseEntity<Set<CouponDto>> getAllCoupon(@PathVariable String token) {
        return ResponseEntity.ok(customerService.allCouponsPurchased(customerSessionManager.handleToken(token)));
    }

    @GetMapping("/unclaimed-coupons/{token}")
    public ResponseEntity<Set<CouponDto>> allUnclaimedCoupons(@PathVariable String token) {
        return ResponseEntity.ok(customerService.allUnclaimedCoupons(customerSessionManager.handleToken(token)));
    }

    @GetMapping("/Expires-soon/{token}")
    public ResponseEntity<Set<CouponDto>> expiredSoon(@PathVariable String token) {
        return ResponseEntity.ok(customerService.expiresThisWeek(customerSessionManager.handleToken(token)));
    }
}
