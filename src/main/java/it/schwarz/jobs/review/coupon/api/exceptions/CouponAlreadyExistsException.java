package it.schwarz.jobs.review.coupon.api.exceptions;

public class CouponAlreadyExistsException extends BusinessException {
    public CouponAlreadyExistsException(String detail) {
        super(detail);
    }
}
