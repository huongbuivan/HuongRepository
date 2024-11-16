package com.rdjava.springboot.repository;

import com.rdjava.springboot.model.Product;
import com.rdjava.springboot.model.ProductOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    TestEntityManager entityManager;

    Product product1;
    Set<ProductOrder> productOrders1 = new HashSet<>();
    @BeforeEach
    public void setup() {
        product1 = new Product();
        product1.setId(Long.valueOf(1));
        product1.setName("Kiwi");
        product1.setPrice(BigDecimal.valueOf(100.00));
        product1.setDescription("Product init");

        ProductOrder productOrder = new ProductOrder();
        productOrder.setQuantity(10);

        Product prdOrder = new Product();
        prdOrder.setId(Long.valueOf(2));
        prdOrder.setName("Apple");
        prdOrder.setPrice(BigDecimal.valueOf(100.00));
        prdOrder.setDescription("Product order");

        productOrder.setProduct(prdOrder);

        productOrders1.add(productOrder);

        product1.setProductOrders(productOrders1);
    }

    @DisplayName("Test insert product case successfully")
    @Test
    void createProductSuccessfully() {
        Product insertedPro = productRepository.save(product1);
        Product pro = entityManager.find(Product.class, insertedPro.getId());
        assertThat(pro).usingComparatorForFields((x,y)->0,"productOrders","name", "price", "description").isEqualToComparingFieldByFieldRecursively(product1);
    }

    @DisplayName("Test list name of products when save successful")
    @Test
    public void listAll() {
        productRepository.save(product1);
        List<String> names = productRepository.namesList();
        assertTrue(names.stream().count() > 0);
    }
}
