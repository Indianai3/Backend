package com.service.service.order;

import com.service.model.PaymentInfo;
import com.service.model.entity.Order;
import com.service.model.entity.OrderStatus;
import com.service.service.postorder.PostOrderProcessor;
import com.service.service.postorder.event.AdminEmailNotifierEvent;
import com.service.service.postorder.event.UserEmailNotifierEvent;
import com.service.service.postorder.event.UserSessionEvent;
import com.service.service.postorder.event.WhatsappNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookService {

    private final OrderService orderService;
    private static final PostOrderProcessor postOrderProcessor = new PostOrderProcessor(
            new WhatsappNotificationEvent(),
            new AdminEmailNotifierEvent(),
            new UserEmailNotifierEvent(),
            new UserSessionEvent());

    public void handlePaymentCallback(JSONObject jsonObject) {
        processWebhook(jsonObject);
    }

    private void processWebhook(JSONObject webhookPayload) {
        String event = webhookPayload.getString("event");
        JSONObject payload = webhookPayload.getJSONObject("payload");

        switch (event) {
            case "payment.captured":
                handlePaymentCaptured(payload.getJSONObject("payment").getJSONObject("entity"));
                break;
            case "payment.failed":
                handlePaymentFailed(payload.getJSONObject("payment").getJSONObject("entity"));
                break;
            // Add more cases to handle other events
            default:
                log.info("Unhandled event type: " + event);
        }
    }

    @SneakyThrows
    private void handlePaymentCaptured(JSONObject payment) {
        String status = payment.getString("status");
        String description = payment.optString("description", "No description available");

        // Create a PaymentInfo object for captured payment
        PaymentInfo paymentInfo = new PaymentInfo(status, description);

        // todo: move this logic to update order event in postorder
        String orderId = getOrderId(payment);
        Order order = orderService.getOrder(orderId);
        order.getOrderInfo().setOrderStatus(OrderStatus.PROCESSING);
        order.setPaymentInfo(paymentInfo);
        orderService.updateOrder(order);

        postOrderProcessor.processPostOrderEvents(order);

        // Process the payment captured event
        log.info("Payment captured: " + paymentInfo);
    }

    private void handlePaymentFailed(JSONObject payment) {
        String status = payment.getString("status");
        String description = payment.optString("error_description", "No error description available");

        // Create a PaymentInfo object for failed payment
        PaymentInfo paymentInfo = new PaymentInfo(status, description);

        String orderId = getOrderId(payment);
        Order order = orderService.getOrder(orderId);
        order.getOrderInfo().setOrderStatus(OrderStatus.CANCELLED);
        order.setPaymentInfo(paymentInfo);
        orderService.updateOrder(order);

        // Process the payment failed event
        log.info("Payment failed: " + paymentInfo);
    }

    @SneakyThrows
    private static String getOrderId(JSONObject payment) {
        String orderId = payment.getString("order_id");

        if(orderId == null) {
            log.error("Order Id not found in payment entity");
            throw new Exception("Order Id not found in payment entity");
        }

        return orderId;
    }
}
