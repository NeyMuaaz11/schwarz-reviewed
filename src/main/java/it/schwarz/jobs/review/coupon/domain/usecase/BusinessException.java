package it.schwarz.jobs.review.coupon.domain.usecase;

public class BusinessException extends RuntimeException {
    public BusinessException(String detail) {
        super(detail);
    }

}
