package com.aarthi.ms.inventoryservice.service;

import com.aarthi.ms.inventoryservice.dto.InventoryResponse;
import com.aarthi.ms.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {
    private final InventoryRepository repository;

    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return repository.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();
    }
}
