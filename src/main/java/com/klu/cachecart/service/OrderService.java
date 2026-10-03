package com.klu.cachecart.service;

import com.klu.cachecart.Entity.Order;
import com.klu.cachecart.Entity.OrderItem;
import com.klu.cachecart.Entity.Product;
import com.klu.cachecart.repository.OrderItemRepository;
import com.klu.cachecart.repository.OrderRepository;
import com.klu.cachecart.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;

        this.orderItemRepository = orderItemRepository;

        this.productRepository = productRepository;
    }

    public List<Order> getOrders(String userId) {

        List<Order> orders =
                orderRepository.findByUserId(userId);

        for (Order order : orders) {

            order.setItems(
                    orderItemRepository.findByOrderId(
                            order.getId()
                    )
            );
        }

        return orders;
    }

    @Transactional
    public Order createOrder(Order order) {

        order.setId(
                "CC-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
        );

        Order savedOrder =
                orderRepository.save(order);

        if (order.getItems() != null) {

            for (OrderItem item : order.getItems()) {

                item.setId(
                        "item-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                );

                item.setOrderId(
                        savedOrder.getId()
                );

                orderItemRepository.save(item);

                Product product = productRepository
                        .findById(item.getProductId())
                        .orElse(null);

                if (product != null) {

                    product.setStock(
                            Math.max(
                                    0,
                                    product.getStock()
                                            - item.getQuantity()
                            )
                    );

                    productRepository.save(product);
                }
            }
        }

        savedOrder.setItems(
                orderItemRepository.findByOrderId(
                        savedOrder.getId()
                )
        );

        return savedOrder;
    }

    public List<OrderItem> getOrderItems(String orderId) {

        return orderItemRepository.findByOrderId(orderId);
    }
}