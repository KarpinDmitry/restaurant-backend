package ru.karpin.restaurant.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemCreate(@NotNull Long dishId, @NotNull @Min(1) Integer quantity) {
}
