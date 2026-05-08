package ru.karpin.restaurant.photo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.common.storage.FileStorageService;
import ru.karpin.restaurant.photo.dto.PhotoResponse;
import ru.karpin.restaurant.photo.entity.Photo;
import ru.karpin.restaurant.photo.mapper.PhotoMapper;
import ru.karpin.restaurant.photo.repository.PhotoRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoService {

    private static final String SUBDIRECTORY = "photos";

    private final PhotoRepository repository;
    private final PhotoMapper mapper;
    private final FileStorageService fileStorage;

    public PhotoResponse upload(MultipartFile file) {
        fileStorage.validateImage(file);
        String urlPath = fileStorage.store(file, SUBDIRECTORY);

        try {
            Photo photo = new Photo();
            photo.setPath(urlPath);
            Photo saved = repository.saveAndFlush(photo);
            return mapper.toResponse(saved);
        } catch (RuntimeException e) {
            fileStorage.delete(urlPath);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public PhotoResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public void delete(Long id) {
        Photo photo = repository.findById(id).orElseThrow(() -> notFound(id));
        repository.delete(photo);
        repository.flush();
        fileStorage.delete(photo.getPath());
    }

    private ResourceNotFoundException notFound(Long id) {
        return new ResourceNotFoundException("Photo with id " + id + " not found");
    }
}
