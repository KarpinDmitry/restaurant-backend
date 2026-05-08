package ru.karpin.restaurant.photo.mapper;

import org.springframework.stereotype.Component;
import ru.karpin.restaurant.photo.dto.PhotoResponse;
import ru.karpin.restaurant.photo.entity.Photo;

@Component
public class PhotoMapper {

    public PhotoResponse toResponse(Photo entity) {
        return new PhotoResponse(entity.getId(), entity.getPath());
    }
}
