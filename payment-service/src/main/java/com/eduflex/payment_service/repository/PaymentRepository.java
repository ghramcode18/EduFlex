package com.eduflex.payment_service.repository;

import com.eduflex.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByUserId(String userId);
    List<Payment> findByCourseId(String courseId);
}
