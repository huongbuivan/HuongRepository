package com.rdjava.springboot.service.impl;

import com.rdjava.springboot.constant.ResponseCode;
import com.rdjava.springboot.dto.resquests.InsertOrderRequest;
import com.rdjava.springboot.exception.AppException;
import com.rdjava.springboot.model.Customer;
import com.rdjava.springboot.model.Order;
import com.rdjava.springboot.model.Product;
import com.rdjava.springboot.model.ProductOrder;
import com.rdjava.springboot.repository.CustomerRepository;
import com.rdjava.springboot.repository.OrderRepository;
import com.rdjava.springboot.repository.ProductRepository;
import com.rdjava.springboot.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void insert(InsertOrderRequest request) {
        log.warn("Continue with creating order business flow");
        Order order = new Order();
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());

        Set<ProductOrder> productOrders = new HashSet<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        log.info("Continue with creating product orders flow");
        for (InsertOrderRequest.ProductOrderRequest prdOrderRequest : request.getProductOrders()) {
            Product product = productRepository.findById(prdOrderRequest.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(product);
            productOrder.setOrder(order);
            productOrder.setQuantity(prdOrderRequest.getQuantity());
            productOrders.add(productOrder);

            BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(prdOrderRequest.getQuantity()));
            totalAmount = totalAmount.add(productTotal);
        }

        order.setProductOrders(productOrders);
        order.setAmount(totalAmount);


        log.debug("Continue with Customer Order");
        Set<Customer> customers = new HashSet<>();
        for (InsertOrderRequest.CustomerRequest customerRequest : request.getCustomers()) {
            Customer customer = customerRepository.findById(customerRequest.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            customer.getOrders().add(order);
            customers.add(customer);

        }
        order.setCustomers(customers);

        try {
            orderRepository.save(order);
            log.info("Creating the order successfully!!");
        }catch (Exception ex) {
            log.error("Failure in creating order!");
            throw AppException.builder()
                    .responseCode(ResponseCode.FAILED)
                    .errorMessage("Insert order failed")
                    .build();
        }
    }
}
