package com.klu.cachecart.repository;

import com.klu.cachecart.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, String> {

    List<OrderItem> findByOrderId(String orderId);
}