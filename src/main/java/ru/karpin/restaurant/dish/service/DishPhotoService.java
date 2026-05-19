package ru.karpin.restaurant.dish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.dish.dto.DishPhotoCreateRequest;
import ru.karpin.restaurant.dish.dto.DishPhotoResponse;
import ru.karpin.restaurant.dish.entity.Dish;
import ru.karpin.restaurant.dish.repository.DishRepository;
import ru.karpin.restaurant.photo.dto.PhotoResponse;
import ru.karpin.restaurant.photo.entity.Photo;
import ru.karpin.restaurant.photo.mapper.PhotoMapper;
import ru.karpin.restaurant.photo.repository.PhotoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DishPhotoService {

    private final DishRepository dishRepository;
    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    @Transactional(readOnly = true)
    public ListResponse<PhotoResponse> findPhotos(Long dishId) {
        Dish dish = loadDish(dishId);
        List<PhotoResponse> data = dish.getPhotos().stream()
                .map(photoMapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    public DishPhotoResponse link(Long dishId, DishPhotoCreateRequest request) {
        Dish dish = loadDish(dishId);
        Photo photo = photoRepository.findById(request.photoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Photo with id " + request.photoId() + " not found"));
        dish.getPhotos().add(photo); // Set semantics: dup-add is a no-op
        return new DishPhotoResponse(dishId, photo.getId());
    }

    public void unlink(Long dishId, Long photoId) {
        Dish dish = loadDish(dishId);
        boolean removed = dish.getPhotos().removeIf(p -> p.getId().equals(photoId));
        if (!removed) {
            throw new ResourceNotFoundException(
                    "Photo " + photoId + " is not linked to dish " + dishId);
        }
    }

    private Dish loadDish(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dish with id " + dishId + " not found"));
    }
}
