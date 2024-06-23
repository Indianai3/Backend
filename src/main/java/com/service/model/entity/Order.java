package com.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.service.model.PaymentInfo;
import com.service.model.request.CheckoutRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    //todo: design Order class
    private String orderId;
    private String userId;
    private CheckoutRequest checkoutRequest;
    private Integer amount;
    private Long orderTimestamp;
    private OrderInfo orderInfo;
    private PaymentInfo paymentInfo;
}
