package ru.karpin.restaurant.dish.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DishUpdateRequest(
        @Size(max = 255)
        String name,

        @Positive
        Double weight,

        @PositiveOrZero
        Integer calories,

        @DecimalMin(value = "0.00")
        BigDecimal price,

        String description
) {
}
