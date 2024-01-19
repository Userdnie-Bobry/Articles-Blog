package org.articlesblog.services.firebase;

import com.google.cloud.storage.*;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.articlesblog.config.FirebaseConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {
    private static final String BUCKET_NAME = "articles-b1def.appspot.com";
    private final Storage storage;
    private static final String IMAGE_NAME = "https://firebasestorage.googleapis.com/v0/b/articles-b1def.appspot.com/o/article-image.jpg?alt=media&token=b53c0587-d2d6-4e5f-b788-0c7bae76d2ba";

    public FirebaseStorageServiceImpl() throws IOException {
        FirebaseConfig config = new FirebaseConfig();
        storage = config.getStorage();
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
                Path path = Paths.get(fileName);
                Files.copy(file.getInputStream(), path);

                Thumbnails.of(path.toFile())
                        .crop(Positions.CENTER)
                        .size(660, 360)
                        .toFile(path.toFile());

                BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
                Blob blob = storage.create(blobInfo, Files.readAllBytes(path));
                Files.delete(path);

                return "https://firebasestorage.googleapis.com/v0/b/" + blob.getBucket() + "/o/" +
                        encodeURIComponent(blob.getName()) + "?alt=media&token=" + UUID.randomUUID();
            }
            else {
                return IMAGE_NAME;
            }
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
        if (!Objects.equals(id, IMAGE_NAME)) {
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
