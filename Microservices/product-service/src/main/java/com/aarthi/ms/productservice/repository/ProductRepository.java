package com.aarthi.ms.productservice.repository;

import com.aarthi.ms.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
