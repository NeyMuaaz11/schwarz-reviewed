package it.schwarz.jobs.review.coupon.api.dto;

import it.schwarz.jobs.review.coupon.domain.entity.ApplicationResult;

import java.math.BigDecimal;

public record ApplyCouponResponseDto(

        BasketDto basket,
        BigDecimal appliedDiscount) {

    public static ApplyCouponResponseDto of(ApplicationResult applicationResult) {
        return new ApplyCouponResponseDto(new BasketDto(
                applicationResult.getBasket().getValue().toBigDecimal()),
                applicationResult.getAppliedCoupon().getDiscount().toBigDecimal()
        );
    }
}
