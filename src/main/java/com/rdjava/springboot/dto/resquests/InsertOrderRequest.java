package com.rdjava.springboot.dto.resquests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class InsertOrderRequest {
    private String address;
    private String note;
    private Set<ProductOrderRequest> productOrders;
    private Set<CustomerRequest> customers;

    @Getter
    @Setter
    public static class ProductOrderRequest {
        @NotNull
        private Long productId;
        @Positive(message = "Quantity must be a positive number")
        private Integer quantity;
    }

    @Getter
    @Setter
    public static class CustomerRequest {
        @NotNull
        private Long customerId;
    }
}
