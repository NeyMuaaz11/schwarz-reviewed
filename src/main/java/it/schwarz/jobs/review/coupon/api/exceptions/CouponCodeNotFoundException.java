package it.schwarz.jobs.review.coupon.api.exceptions;

public class CouponCodeNotFoundException extends BusinessException {
    public CouponCodeNotFoundException(String detail) {
        super(detail);
    }
}
