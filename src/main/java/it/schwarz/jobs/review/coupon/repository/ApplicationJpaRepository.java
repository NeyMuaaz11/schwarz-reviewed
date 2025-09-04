package it.schwarz.jobs.review.coupon.repository;

import it.schwarz.jobs.review.coupon.entity.ApplicationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationJpaRepository extends JpaRepository<ApplicationJpaEntity, String> {


}
