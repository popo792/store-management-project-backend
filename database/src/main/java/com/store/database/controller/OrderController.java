package com.store.database.controller;

import com.store.database.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/total/{orderId}")
    public ResponseEntity<BigDecimal> getOrderTotal(@PathVariable Integer orderId) {
        BigDecimal totalValue = orderRepository.totalCustomerOrderValue(orderId);
        return ResponseEntity.ok(totalValue);
    }
}