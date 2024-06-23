package com.service.service.postorder.event;

import com.service.model.entity.Order;
import com.service.service.session.UserSessionService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public class UserSessionEvent implements PostOrderEvent {

    @Autowired
    private UserSessionService userSessionService;

    @Override
    public void process(Order order) {
        userSessionService.addOrder(order.getUserId(), order.getOrderId());
    }

    @Override
    public int getPriority() {
        return -1;
    }
}
