package com.aarthi.ms.orderservice.controller;

import com.aarthi.ms.orderservice.dto.OrderRequest;
import com.aarthi.ms.orderservice.servcie.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;


    public CompletableFuture<String> fallbackMethod(OrderRequest request, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong. Please order after sometime");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest request) {
        return CompletableFuture.supplyAsync(()->service.placeOrder(request));
    }
}
