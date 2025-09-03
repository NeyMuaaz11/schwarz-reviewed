package it.schwarz.jobs.review.coupon.api.dto;

import it.schwarz.jobs.review.coupon.domain.entity.Coupon;

import java.util.List;

public record GetCouponsResponseDto(List<CouponDto> coupons) {

    public static GetCouponsResponseDto of(List<Coupon> coupons) {
        return new GetCouponsResponseDto(
                coupons.stream()
                        .map(coupon -> new CouponDto(
                                coupon.getCode(),
                                coupon.getDiscount().toBigDecimal(),
                                coupon.getMinBasketValue().toBigDecimal(),
                                coupon.getDescription(),
                                coupon.getApplicationCount()))
                        .toList());
    }
}
