package com.service.service.order;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.service.config.RazorpayConfig;
import com.service.model.entity.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RazorpayService {

    private final RazorpayClient razorpayClient;

    @SneakyThrows
    public RazorpayService(RazorpayConfig razorpayConfig) {
        this.razorpayClient = new RazorpayClient(razorpayConfig.getRzp_key_id(), razorpayConfig.getRzp_key_secret());
    }

    public com.razorpay.Order createOrder(Order order, String fbUid) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", order.getAmount());
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", fbUid);

        return razorpayClient.orders.create(orderRequest);
    }
}
