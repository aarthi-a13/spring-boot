package com.aarthi.ms.orderservice.servcie;

import com.aarthi.ms.orderservice.dto.InventoryResponse;
import com.aarthi.ms.orderservice.dto.OrderRequest;
import com.aarthi.ms.orderservice.model.Order;
import com.aarthi.ms.orderservice.model.OrderLineItems;
import com.aarthi.ms.orderservice.repository.OrderRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final ObservationRegistry observationRegistry;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String placeOrder(OrderRequest request) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems= request.getOrderLineItems().stream()
                .map(orderItemDto -> mapper.map(orderItemDto, OrderLineItems.class)).toList();
        order.setOrderLineItemsModel(orderLineItems);
        orderLineItems.forEach(itm->itm.setOrder(order));

        List<String> skuCodes = order.getOrderLineItemsModel().stream().map(OrderLineItems::getSkuCode).toList();

        //Call inventory service to check the stock
        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
        return inventoryServiceObservation.observe(()->{
            InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                    .uri("http://inventory-service/Inventory/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)//will define the response type
                    .block(); //makes the call as a synchronous
            boolean productsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
            if (productsInStock){
                repository.save(order);
                kafkaTemplate.send("notificationTopic", order.getOrderNumber());
                return "Order placed successfully";
            }
            else throw new IllegalArgumentException("Product is not in stock, please try again later");
        });
    }
}
