package com.rdjava.springboot.service.impl;

import com.rdjava.springboot.constant.LogConstants;
import com.rdjava.springboot.constant.ResponseCode;
import com.rdjava.springboot.dto.responses.ProductResponse;
import com.rdjava.springboot.dto.resquests.ProductRequest;
import com.rdjava.springboot.dto.resquests.UpdateProductRequest;
import com.rdjava.springboot.exception.AppException;
import com.rdjava.springboot.model.Product;
import com.rdjava.springboot.repository.ProductRepository;
import com.rdjava.springboot.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse insert(ProductRequest request) {
        log.debug(LogConstants.LOG_POINT);
        return productRepository.save(request.toProduct()).toProductResponse();
    }

    @Override
    public Optional<ProductResponse> findById(long id) {
        log.debug(LogConstants.LOG_POINT);
        return productRepository.findById(id).map(Product::toProductResponse);
    }

    @Override
    public List<ProductResponse> getAllProduct(String sortBy) {
        log.debug(LogConstants.LOG_POINT);
        return (sortBy == null ? productRepository.findAll() : productRepository.findAll(Sort.by(sortBy))).stream()
                .flatMap(product -> Stream.of(product.toProductResponse()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse update(long id, UpdateProductRequest request) {
        log.debug(LogConstants.LOG_POINT);
        Optional<Product> product = productRepository.findById(id);
        return product.map(prd -> {
            prd.setName(request.getName());
            prd.setDescription(request.getDescription());
            prd.setPrice(request.getPrice());
            productRepository.save(prd);
            return prd.toProductResponse();
        }).orElseThrow(() -> {
            return AppException.builder()
                    .responseCode(ResponseCode.FAILED)
                    .errorMessage("Update product is failed")
                    .build();
        });
    }

    @Override
    public void delete(long id) {
        log.debug(LogConstants.LOG_POINT);
        boolean isExisted = productRepository.existsById(id);
        if(isExisted) {
            Product product = productRepository.findById(id).get();
            productRepository.delete(product);
        } else {
            throw AppException.builder()
                    .responseCode(ResponseCode.FAILED)
                    .errorMessage("The product not found")
                    .build();
        }
    }

    @Override
    public Page<ProductResponse> getAllByPaging(Pageable pageable){
        log.debug(LogConstants.LOG_POINT);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(Product::toProductResponse);
    }
}
