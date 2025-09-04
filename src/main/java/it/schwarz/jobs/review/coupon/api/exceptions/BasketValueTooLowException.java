package it.schwarz.jobs.review.coupon.api.exceptions;

public class BasketValueTooLowException extends BusinessException {
    public BasketValueTooLowException(String detail) {
        super(detail);
    }
}
