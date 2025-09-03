package it.schwarz.jobs.review.coupon.api;

import it.schwarz.jobs.review.coupon.api.dto.*;
import it.schwarz.jobs.review.coupon.domain.usecase.CouponUseCases;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/coupons")
public class CouponRestController {

    private final CouponUseCases couponUseCases;


    public CouponRestController(CouponUseCases couponUseCases) {
        this.couponUseCases = couponUseCases;
    }


    @GetMapping()
    public ResponseEntity<GetCouponsResponseDto> getCoupons() {
        var coupons = couponUseCases.findAllCoupons();

        // Map from Domain to API
        var response = GetCouponsResponseDto.of(coupons);

        return ResponseEntity.ok(response);
    }


    @PostMapping()
    public ResponseEntity<CreateCouponResponseDto> createCoupon(@Valid @RequestBody CreateCouponRequestDto request) {

        // Map from API to Domain
        var coupon = request.toCoupon();

        var couponCreated = couponUseCases.createCoupon(coupon);

        // Map from Domain to API and return
        var response = CreateCouponResponseDto.of(couponCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{couponCode}/applications")
    public ResponseEntity<GetCouponApplicationsResponseDto> getCouponApplications(@PathVariable("couponCode") String couponCode) {
        var couponApplications = couponUseCases.getApplications(couponCode);

        // Map from Domain to API
        var response = GetCouponApplicationsResponseDto.of(couponApplications);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/applications")
    public ResponseEntity<ApplyCouponResponseDto> applyCoupon(@Valid @RequestBody ApplyCouponRequestDto request) {

        // Map from API to Domain
        var basket = request.basket().toBasket();
        var couponCode = request.couponCode();

        var applicationResult = couponUseCases.applyCoupon(basket, couponCode);

        // Map from Domain to API and return
        var response = ApplyCouponResponseDto.of(applicationResult);
        return ResponseEntity.ok(response);
    }

}
