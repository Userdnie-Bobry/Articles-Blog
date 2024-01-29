package org.articlesblog.services.firebase;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseStorageService {
    String uploadImage(MultipartFile file);
    String updateImage(String id, MultipartFile file);
    void deleteImage(String id);
}
