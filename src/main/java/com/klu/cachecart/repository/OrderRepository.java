package com.klu.cachecart.repository;

import com.klu.cachecart.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, String> {

    List<Order> findByUserId(String userId);
}