package org.articlesblog.services.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import jakarta.annotation.PostConstruct;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {
    private static final String FILE_NAME = "src/main/resources/articles-b1def-firebase-adminsdk-e85q5-51852b2062.json";
    private static final String BUCKET_NAME = "articles-b1def.appspot.com";

    private Storage storage;

    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(FILE_NAME);

        storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .getService();
    }


    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path path = Paths.get(fileName);
            Files.copy(file.getInputStream(), path);

            Thumbnails.of(path.toFile())
                    .crop(Positions.CENTER)
                    .size(660,360)
                    .toFile(path.toFile());

            BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            Blob blob = storage.create(blobInfo, Files.readAllBytes(path));
            Files.delete(path);

            return "https://firebasestorage.googleapis.com/v0/b/" + blob.getBucket() + "/o/" +
                    encodeURIComponent(blob.getName()) + "?alt=media&token=" + UUID.randomUUID();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public String updateImage(String id, MultipartFile file) {
        deleteImage(id);
        return uploadImage(file);
    }

    @Override
    public void deleteImage(String id) {
        if(id != null) {
            String fileName = id.substring(id.lastIndexOf("/") + 1, id.indexOf("?"));
            storage.get(BUCKET_NAME, fileName).delete();
        }
    }

    private static String encodeURIComponent(String s) {
        String result;
        result = URLEncoder.encode(s, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20")
                .replaceAll("%21", "!")
                .replaceAll("%27", "'")
                .replaceAll("%28", "(")
                .replaceAll("%29", ")")
                .replaceAll("%7E", "~");
        return result;
    }
}