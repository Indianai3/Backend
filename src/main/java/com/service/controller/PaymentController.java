package com.service.controller;

import com.service.model.entity.Order;
import com.service.model.request.CheckoutRequest;
import com.service.service.order.OrderService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/checkout")
@CrossOrigin
public class PaymentController {
    private final OrderService orderService;

    @PostMapping("/payment/create")
    public Order createOrder(@RequestBody CheckoutRequest checkoutRequest, @RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid) {
        return orderService.createOrder(checkoutRequest, fbUid);
    }
}