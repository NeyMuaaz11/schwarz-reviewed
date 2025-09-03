package it.schwarz.jobs.review.coupon.domain.entity;

import java.math.BigDecimal;

public class AmountOfMoney {
    public static final AmountOfMoney ZERO = new AmountOfMoney(BigDecimal.ZERO);
    private final BigDecimal amount;

    private AmountOfMoney(BigDecimal amount) {
        this.amount = amount;
    }

    public static AmountOfMoney of(String amountAsString) {
        return new AmountOfMoney(new BigDecimal(amountAsString));
    }

    public static AmountOfMoney of(BigDecimal amountAsBigDecimal) {
        return new AmountOfMoney(amountAsBigDecimal);
    }

    public boolean isGreaterThan(AmountOfMoney otherAmount) {
        return (amount.compareTo(otherAmount.amount) > 0);
    }

    public boolean isLessThan(AmountOfMoney otherAmount) {
        return (amount.compareTo(otherAmount.amount) < 0);
    }

    public BigDecimal toBigDecimal() {
        return amount;
    }
}
