package com.service.service.segmid;

import com.google.cloud.firestore.DocumentReference;
import com.service.dao.FirebaseDao;
import com.service.model.entity.SegmidToken;
import com.service.utils.Constants;
import com.service.utils.GenericUtils;
import com.service.utils.exception.BackendException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class SegmidTokenManager {

    private final List<Pair<DocumentReference, SegmidToken>> storeTokens = Collections.synchronizedList(new ArrayList<>());
    private final FirebaseDao firebaseDao;

    @PostConstruct
    private void initStoreTokens() {
        log.info("Initialising store tokens");
        storeTokens.clear();
//        storeTokens.addAll(firebaseDao.getDocuments(Constants.SEGMID_TOKEN_STORE, SegmidToken.class));
        log.info("Successfully initialised store tokens");
    }

    @SneakyThrows
    public Pair<DocumentReference, SegmidToken> getToken() {
        if (storeTokens.isEmpty()) {
            log.info("StoreTokens is null or empty");
            throw new BackendException("We are currently facing heavy load, please wait while we cook your Tshirt");
        }
        return storeTokens.get(ThreadLocalRandom.current().nextInt(storeTokens.size()));
    }

    public synchronized void removeTokenFromStore(Pair<DocumentReference, SegmidToken> tokenRef) {
        log.info("Removing tokenRef from store {}, as its expired", tokenRef);
        storeTokens.remove(tokenRef);
        firebaseDao.deleteDocument(Constants.SEGMID_TOKEN_STORE, tokenRef.getLeft().getId());
        log.info("Removed tokenRef from store {}, as its expired", tokenRef);
    }

    public synchronized Boolean refreshCache() {
        initStoreTokens();
        return true;
    }

    public Boolean addTokens(List<String> segmidTokens) {
        segmidTokens.forEach(segmidToken -> firebaseDao.saveDocument(Constants.SEGMID_TOKEN_STORE, GenericUtils.getUuid(), new SegmidToken(segmidToken)));

        initStoreTokens();
        return true;
    }

    public List<Pair<DocumentReference, SegmidToken>> getAll() {
        return storeTokens;
    }
}
