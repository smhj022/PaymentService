package com.smhj.PaymentService.repositories;


import com.smhj.PaymentService.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderId(String orderId);
//    OrderEntity getByOrderId(String orderID);
}
