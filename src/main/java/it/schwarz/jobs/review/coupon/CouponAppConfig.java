package it.schwarz.jobs.review.coupon;

import it.schwarz.jobs.review.coupon.domain.usecase.CouponProvider;
import it.schwarz.jobs.review.coupon.domain.usecase.CouponUseCases;
import it.schwarz.jobs.review.coupon.provider.inmem.InMemoryCouponProvider;
import it.schwarz.jobs.review.coupon.provider.jpa.ApplicationJpaRepository;
import it.schwarz.jobs.review.coupon.provider.jpa.CouponJpaRepository;
import it.schwarz.jobs.review.coupon.provider.jpa.JpaCouponProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponAppConfig {

    @Bean
    public CouponProvider getJpaCouponProvider(
            CouponJpaRepository couponJpaRepository,
            ApplicationJpaRepository applicationRepository) {
        return new JpaCouponProvider(couponJpaRepository, applicationRepository);
    }

    // Comment in/out one of the CouponProvider Beans to select one for runtime
    // @Bean
    public CouponProvider getInMemCouponProvider() {
        return new InMemoryCouponProvider();
    }


    @Bean
    public CouponUseCases getCouponUseCases(CouponProvider couponProvider) {
        return new CouponUseCases(couponProvider);
    }


}
