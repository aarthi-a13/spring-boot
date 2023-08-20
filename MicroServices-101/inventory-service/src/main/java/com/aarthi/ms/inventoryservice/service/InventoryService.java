package com.aarthi.ms.inventoryservice.service;

import com.aarthi.ms.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final InventoryRepository repository;

    public boolean isInStock(String skuCode) {
        return repository.findBySkuCode(skuCode).isPresent();
    }
}
