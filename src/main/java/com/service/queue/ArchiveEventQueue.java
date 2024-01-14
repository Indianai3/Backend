package com.service.queue;

import com.service.model.entity.ArchiveEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Slf4j
public class ArchiveEventQueue {

    @Getter
    private static final ArchiveEventQueue instance = new ArchiveEventQueue();
    private final BlockingQueue<ArchiveEvent> queue = new LinkedBlockingQueue<>();

    private ArchiveEventQueue() {}

    public void publish(ArchiveEvent message) {
        log.info("Publishing message to queue with docId: {}", message.getDocId());
        boolean offered = queue.offer(message);
        log.info("Published message to queue with docId: {}", message.getDocId());
    }

    public ArchiveEvent poll() {
        return queue.poll();
    }


}
