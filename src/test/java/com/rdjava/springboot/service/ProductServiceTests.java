package com.rdjava.springboot.service;

import com.rdjava.springboot.dto.responses.ProductResponse;
import com.rdjava.springboot.dto.resquests.ProductRequest;
import com.rdjava.springboot.dto.resquests.UpdateProductRequest;
import com.rdjava.springboot.model.Product;
import com.rdjava.springboot.repository.ProductRepository;
import com.rdjava.springboot.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository repo;

    @Test
    @DisplayName("Test Insert product with price > 0 ")
    public void testInsertProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("Product Name");
        request.setDescription("Description ");
        request.setPrice(BigDecimal.valueOf(100.00));
        Product product = request.toProduct();
        product.setId(1L);

        Mockito.when(repo.save(any())).thenReturn(product);
        ProductResponse pro = productService.insert(request);
        assertThat(pro).isEqualToComparingFieldByField(product.toProductResponse());
    }

    @Test
    @DisplayName("Test update product")
    public void testUpdateProduct() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setName("Test Product");
        request.setDescription("Test Product Description field");
        request.setPrice(BigDecimal.valueOf(100.00));

        Product product = request.toProduct();
        product.setId(1L);
        Mockito.when(repo.findById(product.getId())).thenReturn(Optional.of(product));
        Mockito.when(repo.save(product)).thenReturn(product);
        ProductResponse pro = productService.update(product.getId(), request);
        assertThat(pro).isEqualToComparingFieldByField(product.toProductResponse());
    }
}
