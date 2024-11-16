package com.rdjava.springboot.model;

import com.rdjava.springboot.dto.responses.ProductResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductOrder> productOrders = new HashSet<>();

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public ProductResponse toProductResponse() {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setData(this);
        return productResponse;
    }
}
