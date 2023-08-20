package com.aarthi.ms.orderservice.repository;

import com.aarthi.ms.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
