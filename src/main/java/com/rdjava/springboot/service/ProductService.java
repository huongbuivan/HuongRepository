package com.rdjava.springboot.service;

import com.rdjava.springboot.dto.responses.ProductResponse;
import com.rdjava.springboot.dto.resquests.ProductRequest;
import com.rdjava.springboot.dto.resquests.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponse insert(ProductRequest request);
    Optional<ProductResponse> findById(long id);
    List<ProductResponse> getAllProduct(String sortBy);
    ProductResponse update(long id, UpdateProductRequest request);
    void delete(long productId);
    Page<ProductResponse> getAllByPaging(Pageable pageable);
}
