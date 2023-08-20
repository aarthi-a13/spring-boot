package com.aarthi.ms.orderservice.controller;

import com.aarthi.ms.orderservice.dto.OrderRequest;
import com.aarthi.ms.orderservice.servcie.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest request) {
        service.placeOrder(request);
        return "Order placed successfully";
    }
}
