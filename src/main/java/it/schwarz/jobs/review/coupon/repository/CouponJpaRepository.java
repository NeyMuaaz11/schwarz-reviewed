package it.schwarz.jobs.review.coupon.repository;

import it.schwarz.jobs.review.coupon.entity.CouponJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<CouponJpaEntity, String> {


}
