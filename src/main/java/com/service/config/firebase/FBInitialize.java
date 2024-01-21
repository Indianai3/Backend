package com.service.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;

@Service
public class FBInitialize {

    @Value("${firebase.StorageBucketUrl}")
    private String fbStorageBucketUrl;

    @PostConstruct
    public void initialize() {
        try {
            String filePath = "fbkey.json";
            ClassPathResource resource = new ClassPathResource(filePath);
            File file = resource.getFile();
            FileInputStream serviceAccount = new FileInputStream(file);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://projectai-4361d-default-rtdb.firebaseio.com")
                    .setStorageBucket(fbStorageBucketUrl)
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}