package com.aarthi.ms.productservice.service;

import com.aarthi.ms.productservice.dto.ProductRequest;
import com.aarthi.ms.productservice.dto.ProductResponse;
import com.aarthi.ms.productservice.model.Product;
import com.aarthi.ms.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //will create all the necessary constructors during compile time
@Log4j2
public class ProductService {

    private final ModelMapper mapper;
    private final ProductRepository repository;

    public void createProduct(ProductRequest request) {
        Product product = mapper.map(request, Product.class);
        repository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = repository.findAll();
        return products.stream().map(product -> mapper.map(product, ProductResponse.class)).collect(Collectors.toList());
    }
}
