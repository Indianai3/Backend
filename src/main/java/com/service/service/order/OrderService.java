package com.service.service.order;

import com.service.dao.FirebaseDao;
import com.service.model.entity.Order;
import com.service.model.entity.OrderInfo;
import com.service.model.entity.OrderStatus;
import com.service.model.request.CheckoutRequest;
import com.service.service.product.ProductService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final RazorpayService razorpayService;
    private final ProductService productService;
    private final FirebaseDao firebaseDao;

    @SneakyThrows
    public Order createOrder(CheckoutRequest checkoutRequest, String fbUid) {
        Order order = new Order();
        order.setCheckoutRequest(checkoutRequest);
        order.setAmount(getAmount(checkoutRequest) * 100);
        order.setOrderTimestamp(System.currentTimeMillis());
        order.setUserId(fbUid);
        order.setOrderInfo(new OrderInfo(OrderStatus.CREATED));
        com.razorpay.Order razorpayOrder = razorpayService.createOrder(order, fbUid);
        order.setOrderId(razorpayOrder.get("id"));

        // save the order in orders table
        firebaseDao.saveDocument(Constants.ORDERS, order.getOrderId(), order);
        return order;
    }

    @SneakyThrows
    private Integer getAmount(CheckoutRequest checkoutRequest) {
        if (Objects.isNull(checkoutRequest.getProducts()) || checkoutRequest.getProducts().isEmpty()) {
            throw new Exception("No products in the checkout request");
        }

        return checkoutRequest.getProducts()
                .stream()
                .mapToInt(product -> productService.getProduct(product.getProductId()).getPrice() * product.getQuantity())
                .sum();
    }

    public Order getOrder(String orderId) {
        return firebaseDao.getDocument(Constants.ORDERS, orderId, Order.class);
    }

    public void updateOrder(Order order) {
        firebaseDao.saveDocument(Constants.ORDERS, order.getOrderId(), order);
    }
}
