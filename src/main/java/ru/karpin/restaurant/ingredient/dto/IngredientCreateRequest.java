package ru.karpin.restaurant.ingredient.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IngredientCreateRequest(
        @NotBlank
        @Size(max = 255)
        String name
) {
}
