package com.service.service.postorder.event;

import com.service.model.entity.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEmailNotifierEvent implements PostOrderEvent {
    @Override
    public void process(Order order) {
        log.info("processing user email notifier event");
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
