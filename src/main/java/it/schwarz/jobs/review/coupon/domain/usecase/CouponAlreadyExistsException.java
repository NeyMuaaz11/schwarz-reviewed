package it.schwarz.jobs.review.coupon.domain.usecase;

public class CouponAlreadyExistsException extends BusinessException {
    public CouponAlreadyExistsException(String detail) {
        super(detail);
    }
}
