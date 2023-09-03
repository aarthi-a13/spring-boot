package com.aarthi.ms.orderservice.servcie;

import com.aarthi.ms.orderservice.dto.InventoryResponse;
import com.aarthi.ms.orderservice.dto.OrderRequest;
import com.aarthi.ms.orderservice.model.Order;
import com.aarthi.ms.orderservice.model.OrderLineItems;
import com.aarthi.ms.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final ModelMapper mapper;
    private final OrderRepository repository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest request) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsModel(request.getOrderLineItems().stream()
                .map(orderItemDto -> mapper.map(orderItemDto, OrderLineItems.class)).toList());

        List<String> skuCodes = order.getOrderLineItemsModel().stream().map(OrderLineItems::getSkuCode).toList();

        //Call inventory service to check the stock
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/Inventory/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)//will define the response type
                .block(); //makes the call as a synchronous
        boolean productsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if (productsInStock){
            repository.save(order);
            return "Order placed successfully";
        }
        else throw new IllegalArgumentException("Product is not in stock, please try again later");
    }
}
