package com.smhj.PaymentService.repositories;

import com.smhj.PaymentService.models.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}
