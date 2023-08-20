package com.aarthi.ms.productservice;

import com.aarthi.ms.productservice.controller.ProductController;
import com.aarthi.ms.productservice.dto.ProductRequest;
import com.aarthi.ms.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Testcontainers
//@SpringBootTest
@WebMvcTest(ProductController.class)
class ProductServiceApplicationTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService service;


    //This is to test the integration with TestContainers framework and needs docker env
   /* @Container
    static MongoDBContainer container = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }*/

    @Test
    void shouldCreate() throws Exception {
        String product = mapper.writeValueAsString(getProductRequest());
        mvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product))
                .andExpect(status().isCreated());
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder().name("Iphone 13").description("Iphone 13")
                .price(BigDecimal.valueOf(1200)).build();
    }
}
