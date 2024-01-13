package org.articlesblog.services.firebase;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FirebaseStorageService {
    String uploadArticleContent(MultipartFile file, String jsonContent) throws IOException;
    void deleteFile(String fileLink);
}

