package ru.karpin.restaurant.common.storage;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.karpin.restaurant.common.exception.InvalidFileException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class LocalFileStorageService implements FileStorageService {

    private static final Map<String, String> IMAGE_MIME_TO_EXT = Map.of(
            "image/jpeg", "jpg",
            "image/png", "png",
            "image/webp", "webp"
    );

    private final Path uploadRoot;
    private final String urlPrefix;

    public LocalFileStorageService(
            @Value("${app.upload.dir}") String uploadDir,
            @Value("${app.upload.url-prefix}") String urlPrefix
    ) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.urlPrefix = urlPrefix;
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(uploadRoot);
            log.info("Upload root: {}", uploadRoot);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create upload directory: " + uploadRoot, e);
        }
    }

    @Override
    public void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("File is empty");
        }
        String contentType = file.getContentType();
        if (contentType == null || !IMAGE_MIME_TO_EXT.containsKey(contentType.toLowerCase())) {
            throw new InvalidFileException(
                    "Unsupported image type: " + contentType + ". Allowed: " + IMAGE_MIME_TO_EXT.keySet()
            );
        }
    }

    @Override
    public String store(MultipartFile file, String subdirectory) {
        String contentType = file.getContentType().toLowerCase();
        String ext = IMAGE_MIME_TO_EXT.get(contentType);
        String filename = UUID.randomUUID() + "." + ext;

        Path subdir = uploadRoot.resolve(subdirectory).normalize();
        if (!subdir.startsWith(uploadRoot)) {
            throw new IllegalArgumentException("Subdirectory escapes upload root: " + subdirectory);
        }

        try {
            Files.createDirectories(subdir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create subdirectory " + subdir, e);
        }

        Path target = subdir.resolve(filename);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file " + target, e);
        }

        return urlPrefix + "/" + subdirectory + "/" + filename;
    }

    @Override
    public void delete(String urlPath) {
        if (urlPath == null || !urlPath.startsWith(urlPrefix + "/")) {
            log.warn("Attempt to delete file outside upload prefix: {}", urlPath);
            return;
        }
        String relative = urlPath.substring(urlPrefix.length() + 1);
        Path target = uploadRoot.resolve(relative).normalize();

        if (!target.startsWith(uploadRoot)) {
            log.warn("Attempt to delete file outside upload root: {}", target);
            return;
        }

        try {
            Files.deleteIfExists(target);
        } catch (IOException e) {
            log.warn("Failed to delete file {}: {}", target, e.getMessage());
        }
    }
}
