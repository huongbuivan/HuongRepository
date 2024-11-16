package com.rdjava.springboot.controller;

import com.rdjava.springboot.constant.LogConstants;
import com.rdjava.springboot.dto.resquests.InsertOrderRequest;
import com.rdjava.springboot.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("orders")
@Log4j2
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Boolean> addOrder(@RequestBody @Valid InsertOrderRequest orderRequest) {
        log.trace(LogConstants.LOG_POINT +" Starting create order process ...");
        orderService.insert(orderRequest);
        return ResponseEntity.ok(true);

    }
}
