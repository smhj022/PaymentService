package com.smhj.PaymentService.repositories;

import com.smhj.PaymentService.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
