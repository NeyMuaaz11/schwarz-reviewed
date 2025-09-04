package it.schwarz.jobs.review.coupon.provider.inmem;

import it.schwarz.jobs.review.coupon.domain.AmountOfMoney;
import it.schwarz.jobs.review.coupon.domain.Coupon;
import it.schwarz.jobs.review.coupon.domain.CouponApplications;
import it.schwarz.jobs.review.coupon.provider.CouponProvider;

import java.time.Instant;
import java.util.*;

/**
 * InMemory Implementation to simplify local test and development.
 * You can use this if there is no real database available.
 */
public class InMemoryCouponProvider implements CouponProvider {

    private final Set<Coupon> coupons = new HashSet<>();
    private final Map<String, List<Instant>> couponApplications = new HashMap<>();

    public InMemoryCouponProvider() {
        // Test Coupons
        coupons.add(new Coupon("TEST_05_50", AmountOfMoney.of("5.00"), AmountOfMoney.of("50.00"), "5 for 50"));
        coupons.add(new Coupon("TEST_15_100", AmountOfMoney.of("15.00"), AmountOfMoney.of("100.00"), "15 for 100"));
        coupons.add(new Coupon("TEST_40_200", AmountOfMoney.of("40.00"), AmountOfMoney.of("200.00"), "40  for 200"));


        // Test DateTimes
        var applicationDateTimes = new ArrayList<Instant>();
        applicationDateTimes.add(Instant.now().plusSeconds(1));
        applicationDateTimes.add(Instant.now().plusSeconds(2));
        applicationDateTimes.add(Instant.now().plusSeconds(3));
        applicationDateTimes.add(Instant.now().plusSeconds(4));
        couponApplications.put("TEST_05_50", applicationDateTimes);
        couponApplications.put("test", applicationDateTimes);
    }


    @Override
    public Coupon createCoupon(Coupon coupon) {
        // Coupon must not already exist
        var foundCoupon = this.findById(coupon.getCode());
        if (foundCoupon.isPresent()) {
            throw new IllegalStateException("Coupon already exists: " + coupon.getCode());
        }

        coupons.add(coupon);
        return coupon;
    }

    @Override
    public List<Coupon> findAll() {
        return coupons.stream().map(c -> new Coupon(c.getCode(), c.getDiscount(), c.getMinBasketValue(), c.getDescription(), couponApplications.computeIfAbsent(c.getCode(), a -> Collections.emptyList()).size())).toList();
    }

    @Override
    public Optional<Coupon> findById(String couponCode) {
        return coupons.stream()
                .filter(it -> it.getCode().equals(couponCode))
                .findFirst();
    }

    @Override
    public void registerCouponApplication(String couponCode) {
        couponApplications.computeIfAbsent(couponCode, a -> new ArrayList<>()).add(Instant.now());
    }

    @Override
    public Optional<CouponApplications> getCouponApplications(String couponCode) {
        if (this.findById(couponCode).isPresent()) {
            List<Instant> applications = couponApplications.get(couponCode);
            // need to know if coupon exists and is not used yet or if coupon does not exist
            if (Objects.isNull(applications)) {
                return Optional.of(new CouponApplications(couponCode, Collections.emptyList()));
            } else {
                return Optional.of(new CouponApplications(couponCode, applications));
            }
        }
        return Optional.empty();
    }
}
