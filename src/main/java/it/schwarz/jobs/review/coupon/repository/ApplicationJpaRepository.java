package it.schwarz.jobs.review.coupon.repository;

import it.schwarz.jobs.review.coupon.entity.ApplicationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ApplicationJpaRepository extends JpaRepository<ApplicationJpaEntity, Long> {

    @Query("select a.timestamp from ApplicationJpaEntity a where a.couponCode = :couponCode")
    List<Instant> findTimestampsByCouponCode(@Param("couponCode") String couponCode);}
