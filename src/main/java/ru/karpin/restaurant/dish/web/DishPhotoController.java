package ru.karpin.restaurant.dish.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.dish.dto.DishPhotoCreateRequest;
import ru.karpin.restaurant.dish.dto.DishPhotoResponse;
import ru.karpin.restaurant.dish.service.DishPhotoService;
import ru.karpin.restaurant.photo.dto.PhotoResponse;

@RestController
@RequestMapping("/dishes/{dishId}/photos")
@RequiredArgsConstructor
public class DishPhotoController {

    private final DishPhotoService service;

    @GetMapping
    public ListResponse<PhotoResponse> list(@PathVariable Long dishId) {
        return service.findPhotos(dishId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishPhotoResponse link(
            @PathVariable Long dishId,
            @Valid @RequestBody DishPhotoCreateRequest request
    ) {
        return service.link(dishId, request);
    }

    @DeleteMapping("/{photoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlink(@PathVariable Long dishId, @PathVariable Long photoId) {
        service.unlink(dishId, photoId);
    }
}
