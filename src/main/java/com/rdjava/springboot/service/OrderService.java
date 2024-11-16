package com.rdjava.springboot.service;

import com.rdjava.springboot.dto.resquests.InsertOrderRequest;

public interface OrderService {
    void insert(InsertOrderRequest request);
}
