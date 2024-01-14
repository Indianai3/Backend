package com.service.queue;

import com.service.dao.FirebaseDao;
import com.service.model.Collections;
import com.service.model.entity.ArchiveEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArchiveMessageProcessor {
    private final FirebaseDao firebaseDao;

    @Scheduled(fixedDelay = 10000)
    public void process() {
        ArchiveEvent event;
        while ((event = ArchiveEventQueue.getInstance().poll()) != null) {
            log.info("Saving ArchiveEvent with id: {}, imageId: {}, userId: {}",
                    event.getDocId(), event.getImageId(), event.getRequestedUserUid());
            firebaseDao.saveDocument(Collections.ARCHIVES, event.getDocId(), event);
            log.info("Successfully saved ArchiveEvent with id: {}, imageId: {}, userId: {}",
                    event.getDocId(), event.getImageId(), event.getRequestedUserUid());
        }
    }
}
