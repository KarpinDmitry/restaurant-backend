package ru.karpin.restaurant.avatar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.karpin.restaurant.avatar.dto.AvatarResponse;
import ru.karpin.restaurant.avatar.entity.Avatar;
import ru.karpin.restaurant.avatar.mapper.AvatarMapper;
import ru.karpin.restaurant.avatar.repository.AvatarRepository;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.common.storage.FileStorageService;


@Service
@RequiredArgsConstructor
@Transactional
public class AvatarService {

    private static final String SUBDIRECTORY = "avatars";
    private final AvatarRepository repository;
    private final FileStorageService fileStorage;

    public AvatarResponse upload(MultipartFile file) {
        fileStorage.validateImage(file);
        String urlPath = fileStorage.store(file, SUBDIRECTORY);

        try {
            Avatar avatar = new Avatar();
            avatar.setPath(urlPath);
            Avatar saved = repository.saveAndFlush(avatar);
            return AvatarMapper.toResponse(saved);
        } catch (RuntimeException e) {
            fileStorage.delete(urlPath);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public AvatarResponse findById(Long id) {
        return AvatarMapper.toResponse(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public Avatar findEntityById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> notFound(id));
    }

    public void delete(Long id) {
        Avatar avatar = repository.findById(id).orElseThrow(() -> notFound(id));
        repository.delete(avatar);
        repository.flush();
        fileStorage.delete(avatar.getPath());
    }

    private ResourceNotFoundException notFound(Long id) {
        return new ResourceNotFoundException("Avatar with id " + id + " not found");
    }
}
