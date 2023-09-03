package com.aarthi.ms.inventoryservice.service;

import com.aarthi.ms.inventoryservice.dto.InventoryResponse;
import com.aarthi.ms.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class InventoryService {
    private final InventoryRepository repository;

    @SneakyThrows //Consumes exceptions without throwing. Should not be used in prod
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        //Simulation to test timeout scenario in order service using resilience4j
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait ended");

        return repository.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();
    }
}
