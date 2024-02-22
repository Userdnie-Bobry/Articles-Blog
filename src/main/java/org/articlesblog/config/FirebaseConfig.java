package org.articlesblog.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Getter
public class FirebaseConfig {
    private static final String FILE_NAME = "articles-b1def-firebase-adminsdk-e85q5-51852b2062.json";

    private Storage storage;

    public FirebaseConfig() throws IOException {
        log.info("Инициализацие Firebase...");
        init();
        log.info("Firebase инициализировался.");
    }

    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(FILE_NAME);

        storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .getService();
    }
}