package com.klu.cachecart.controller;

import com.klu.cachecart.Entity.Order;
import com.klu.cachecart.Entity.OrderItem;
import com.klu.cachecart.Entity.User;
import com.klu.cachecart.service.OrderService;
import com.klu.cachecart.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(
            OrderService orderService,
            UserService userService) {

        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                orderService.getOrders(user.getId())
        );
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody Order order,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        order.setUserId(user.getId());

        Order savedOrder =
                orderService.createOrder(order);

        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> getOrderItems(
            @PathVariable String orderId) {

        return ResponseEntity.ok(
                orderService.getOrderItems(orderId)
        );
    }
}