package com.aarthi.ms.inventoryservice.config;

import com.aarthi.ms.inventoryservice.model.Inventory;
import com.aarthi.ms.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.InputStream;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository repository;
    private final ObjectMapper mapper;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0)
            try (InputStream stream = TypeReference.class.getResourceAsStream("/data/products.json")) {
                repository.saveAll(mapper.readValue(stream, new TypeReference<List<Inventory>>() {
                }));
            }
    }
}
