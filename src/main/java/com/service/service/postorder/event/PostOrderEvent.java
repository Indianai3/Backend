package com.service.service.postorder.event;

import com.service.model.entity.Order;

public interface PostOrderEvent {
    void process(Order order);
    int getPriority();
}
