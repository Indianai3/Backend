package com.service.service.postorder;

import com.service.model.entity.Order;
import com.service.service.postorder.event.PostOrderEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class PostOrderProcessor {
    private final List<PostOrderEvent> postOrderEvents;

    public PostOrderProcessor(PostOrderEvent ... postOrderEvents) {
        this.postOrderEvents = Arrays.stream(postOrderEvents).toList();
    }

    public PostOrderProcessor() {
        this.postOrderEvents = new ArrayList<>();
    }

    public void processPostOrderEvents(Order order) {
        log.info("Processing post order events");
        postOrderEvents.stream()
                .sorted((o1, o2) -> o2.getPriority() - o1.getPriority())
                .forEach(postOrderEvent -> {
                    log.info("Processing post order event: {}", postOrderEvent.getClass().getSimpleName());
                    postOrderEvent.process(order);
                });
    }

}
