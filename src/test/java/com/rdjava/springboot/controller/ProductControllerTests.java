package com.rdjava.springboot.controller;

import com.rdjava.springboot.dto.resquests.UpdateProductRequest;
import com.rdjava.springboot.model.Product;
import com.rdjava.springboot.repository.ProductRepository;
import com.rdjava.springboot.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    List<Product> products1;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        products1 = new ArrayList<Product>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Kiwi");
        product1.setPrice(BigDecimal.valueOf(100.00));
        product1.setDescription("Description 1");
        products1.add(product1);

        product1 = new Product();
        product1.setName("Apple");
        product1.setPrice(BigDecimal.valueOf(200.00));
        product1.setDescription("Description 2");
        products1.add(product1);

        product1 = new Product();
        product1.setName("Banana");
        product1.setPrice(BigDecimal.valueOf(300.00));
        product1.setDescription("Description 3");
        products1.add(product1);

        product1 = new Product();
        product1.setName("Durian");
        product1.setDescription("Description 4");
        product1.setPrice(BigDecimal.valueOf(400.00));
        products1.add(product1);
    }

    @Test
    @DisplayName("Test get list product API")
    void testGetProduct() throws Exception {
        when(productService.getAllProduct(null)).thenReturn(products1.stream()
                .map(Product::toProductResponse)
                .collect(Collectors.toList()));
        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Test update product API")
    void testUpdateProduct() throws Exception {
        UpdateProductRequest request = new UpdateProductRequest();
        Product product1 = products1.get(0);
        product1.setDescription("New description");
        when(productRepository.existsById(product1.getId())).thenReturn(true);
        when(productService.update(eq(product1.getId().longValue()), any())).thenReturn(product1.toProductResponse());
        mockMvc.perform(put(String.format("/products/%s/update", product1.getId()))
                        .content(objectMapper.writeValueAsString(product1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test delete product API")
    void testDeleteProduct() throws Exception {
        Long productId = products1.get(0).getId();
        when(productRepository.existsById(productId)).thenReturn(true);

        mockMvc.perform(delete(String.format("/products/%d", productId)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(productService, times(1)).delete(productId);
    }
}
