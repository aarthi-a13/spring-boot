package com.aarthi.ms.orderservice.servcie;

import com.aarthi.ms.orderservice.dto.OrderRequest;
import com.aarthi.ms.orderservice.model.Order;
import com.aarthi.ms.orderservice.model.OrderLineItems;
import com.aarthi.ms.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final ModelMapper mapper;
    private final OrderRepository repository;

    public void placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItems(request.getOrderLineItems().stream()
                .map(orderItemDto -> mapper.map(orderItemDto, OrderLineItems.class)).toList());
        repository.save(order);

    }
}
