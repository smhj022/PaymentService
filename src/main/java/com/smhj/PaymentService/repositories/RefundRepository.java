package com.smhj.PaymentService.repositories;

import com.smhj.PaymentService.models.RefundModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<RefundModel, Long> {
}
