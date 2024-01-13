package org.articlesblog.services.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {
    private final Storage storage;

    public FirebaseStorageServiceImpl() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadArticleContent(MultipartFile file, String jsonContent) throws IOException {
        String fileName = file.getOriginalFilename();
        String bucketName = "articles-blog-4e455.appspot.com";

        byte[] fileBytes = file.getBytes();
        assert fileName != null;
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();
        Blob blob = storage.create(blobInfo, fileBytes);

        return blob.getMediaLink();
    }

    public void deleteFile(String fileLink) {
        String bucketName = "articles-blog-4e455.appspot.com";
        String fileName = fileLink.substring(fileLink.lastIndexOf("/") + 1);

        storage.get(bucketName, fileName).delete();
    }
}