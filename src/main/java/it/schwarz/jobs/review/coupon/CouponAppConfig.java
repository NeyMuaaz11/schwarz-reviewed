package it.schwarz.jobs.review.coupon;

import it.schwarz.jobs.review.coupon.provider.CouponProvider;
import it.schwarz.jobs.review.coupon.service.CouponService;
import it.schwarz.jobs.review.coupon.provider.inmem.InMemoryCouponProvider;
import it.schwarz.jobs.review.coupon.repository.ApplicationJpaRepository;
import it.schwarz.jobs.review.coupon.repository.CouponJpaRepository;
import it.schwarz.jobs.review.coupon.provider.jpa.JpaCouponProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponAppConfig {

//    @Bean
    public CouponProvider getJpaCouponProvider(
            CouponJpaRepository couponJpaRepository,
            ApplicationJpaRepository applicationRepository) {
        return new JpaCouponProvider(couponJpaRepository, applicationRepository);
    }

    // Comment in/out one of the CouponProvider Beans to select one for runtime, and modify data.sql accordingly
     @Bean
    public CouponProvider getInMemCouponProvider() {
        return new InMemoryCouponProvider();
    }

}
