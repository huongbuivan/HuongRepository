package com.rdjava.springboot.controller;

import com.rdjava.springboot.constant.LogConstants;
import com.rdjava.springboot.dto.responses.ProductResponse;
import com.rdjava.springboot.dto.resquests.ProductRequest;
import com.rdjava.springboot.dto.resquests.UpdateProductRequest;
import com.rdjava.springboot.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
@AllArgsConstructor
@Log4j2
public class ProductController {
    @Autowired
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> insert(@RequestBody @Valid ProductRequest request) {
        log.trace(LogConstants.LOG_POINT);
        ProductResponse insertedProduct = productService.insert(request);
        return ResponseEntity.ok(insertedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductResponse>> getById(@PathVariable("id") long id) {
        log.trace(LogConstants.LOG_POINT);
        Optional<ProductResponse> product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getAllProduct(@RequestParam(required = false) String sortBy) {
        log.trace(LogConstants.LOG_POINT);
        List<ProductResponse> productList = productService.getAllProduct(sortBy);
        return ResponseEntity.ok(productList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") long id){
        log.trace(LogConstants.LOG_POINT);
        productService.delete(id);
        return  ResponseEntity.ok(id);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ProductResponse> update(@PathVariable("id") long id,
                                                  @RequestBody @Valid UpdateProductRequest request){
        log.trace(LogConstants.LOG_POINT);
        ProductResponse productResponse = productService.update(id, request);
        return  ResponseEntity.ok(productResponse);
    }

    @GetMapping("/getAllByPaging")
    public ResponseEntity<Page<ProductResponse>> getAllByPaging(@PageableDefault(page = 0, size = 10) Pageable pageable){
        log.trace(LogConstants.LOG_POINT);
        Page<ProductResponse> productList = productService.getAllByPaging(pageable);
        return ResponseEntity.ok(productList);
    }
}
