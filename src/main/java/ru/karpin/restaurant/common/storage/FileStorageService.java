package ru.karpin.restaurant.common.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    /** Validates the file is a supported image. Throws InvalidFileException otherwise. */
    void validateImage(MultipartFile file);

    /** Stores the file under uploadDir/subdirectory and returns its URL path (e.g. "/uploads/photos/abc.jpg"). */
    String store(MultipartFile file, String subdirectory);

    /** Idempotent — silently ignores missing files and paths outside the upload root. */
    void delete(String urlPath);
}
