package com.service.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.service.utils.exception.BackendException;
import com.service.utils.GenericUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseDao {

    private final Firestore firestore;

    public FirebaseDao() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public String saveDocument(String collectionName, String documentId, Object data) {
        log.info("Saving document with id: {}, in collection: {}", documentId, collectionName);
        CollectionReference collectionReference = firestore.collection(collectionName);
        DocumentReference documentReference = collectionReference.document(documentId);
        documentReference.set(data);
        log.info("Successfully saved document with id: {}, in collection: {}", documentId, collectionName);
        return documentId;
    }

    public String saveDocument(String collectionName, Object data) {
        String documentId = GenericUtils.getUuid();
        return saveDocument(collectionName, documentId, data);
    }

    @SneakyThrows
    public <T> T getDocument(String collectionName, String documentId, Class<T> clazz) {
        CollectionReference collectionReference = firestore.collection(collectionName);
        DocumentReference documentReference = collectionReference.document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        try {
            DocumentSnapshot documentSnapshot = future.get();
            if (documentSnapshot.exists()) {
                return documentSnapshot.toObject(clazz);
            } else {
                throw new BackendException(String.format("document with id: %s not found", documentId), 404);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new BackendException(e);
        } catch (BackendException e) {
            throw new BackendException(e);
        }
    }

    public <T> List<T> getAllDocuments(String collectionName, Class<T> clazz) {
        CollectionReference collectionReference = firestore.collection(collectionName);
        ApiFuture<QuerySnapshot> future = collectionReference.get();
        try {
            QuerySnapshot querySnapshot = future.get();
            return querySnapshot.toObjects(clazz);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exceptions appropriately in a real application
        }
        return null;
    }

    public void updateDocument(String collectionName, String documentId, Object data) {
        CollectionReference collectionReference = firestore.collection(collectionName);
        DocumentReference documentReference = collectionReference.document(documentId);
        documentReference.set(data, SetOptions.merge());
    }

    public void deleteDocument(String collectionName, String documentId) {
        CollectionReference collectionReference = firestore.collection(collectionName);
        DocumentReference documentReference = collectionReference.document(documentId);
        documentReference.delete();
    }
}
