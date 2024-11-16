package com.rdjava.springboot.repository;

import com.rdjava.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT name FROM product ORDER BY name", nativeQuery = true)
    List<String> namesList();
}
