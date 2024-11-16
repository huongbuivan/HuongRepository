package com.rdjava.springboot.dto.responses;

import com.rdjava.springboot.model.Order;
import com.rdjava.springboot.model.ProductOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String address;
    private String note;
    private BigDecimal amount;
    private Set<ProductOrder> productOrders = new HashSet<ProductOrder>();

    public void setData(Order order) {
        this.id = order.getId();
        this.address = order.getAddress();
        this.note = order.getNote();
        this.amount = order.getAmount();
        this.productOrders = order.getProductOrders();
    }
}
