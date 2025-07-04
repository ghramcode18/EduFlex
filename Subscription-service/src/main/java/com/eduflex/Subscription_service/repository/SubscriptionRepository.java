package com.eduflex.Subscription_service.repository;

import com.eduflex.Subscription_service.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    List<Subscription> findByUserId(String userId);
    List<Subscription> findByCourseId(String courseId);
    boolean existsByUserIdAndCourseId(String userId, String courseId);

    Optional<Subscription> findByUserIdAndCourseId(String userId, String courseId);
}
