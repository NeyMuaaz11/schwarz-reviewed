package it.schwarz.jobs.review.coupon.api.dto.applycoupon;

import it.schwarz.jobs.review.coupon.domain.AmountOfMoney;
import it.schwarz.jobs.review.coupon.domain.Basket;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BasketDto(
        @NotNull
        @Min(0)
        @Max(10000)
        BigDecimal value
) {
    public Basket toBasket() {
        return new Basket(AmountOfMoney.of(value));
    }

}
