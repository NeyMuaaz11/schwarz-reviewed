package it.schwarz.jobs.review.coupon.api.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String detail) {
        super(detail);
    }

}
