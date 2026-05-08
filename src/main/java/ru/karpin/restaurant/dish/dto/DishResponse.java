package ru.karpin.restaurant.dish.dto;

import java.math.BigDecimal;

public record DishResponse(
        Long id,
        String name,
        Double weight,
        Integer calories,
        BigDecimal price,
        String description
) {
}
