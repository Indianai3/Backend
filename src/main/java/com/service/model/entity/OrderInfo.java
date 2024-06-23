package com.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderInfo {
    private OrderStatus orderStatus;
    private List<String> updates;

    public OrderInfo(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}