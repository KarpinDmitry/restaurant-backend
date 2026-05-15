package ru.karpin.restaurant.dish.dto;

import jakarta.validation.constraints.NotNull;

public record DishPhotoCreateRequest(
        @NotNull
        Long photoId
) {
}
