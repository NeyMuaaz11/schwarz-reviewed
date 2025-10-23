package it.schwarz.jobs.review.coupon.api.dto.createcoupon;

import it.schwarz.jobs.review.coupon.api.dto.CouponDto;
import it.schwarz.jobs.review.coupon.domain.Coupon;

public record CreateCouponResponseDto(CouponDto coupon) {

    public static CreateCouponResponseDto of(Coupon coupon) {
        return new CreateCouponResponseDto(
                new CouponDto(
                        coupon.getCode(),
                        coupon.getDiscount().toBigDecimal(),
                        coupon.getMinBasketValue().toBigDecimal(),
                        coupon.getDescription(),
                        coupon.getApplicationCount()));
    }
}
