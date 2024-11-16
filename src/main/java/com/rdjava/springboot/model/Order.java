package com.rdjava.springboot.model;

import com.rdjava.springboot.dto.responses.OrderResponse;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "address", nullable = false)
    @Length(min = 1, message = "*Name must have at least 1 characters")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "amount", nullable = false)
    @DecimalMin(value = "0.00", message = "*Amount has to be non negative number")
    private BigDecimal amount;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductOrder> productOrders = new HashSet<>();

    @ManyToMany(mappedBy = "orders", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Customer> customers = new HashSet<>();

    public OrderResponse toOrderResponse(){
        OrderResponse order = new OrderResponse();
        order.setData(this);
        return order;
    }
}
