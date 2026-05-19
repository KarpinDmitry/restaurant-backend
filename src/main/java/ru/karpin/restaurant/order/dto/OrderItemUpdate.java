package ru.karpin.restaurant.order.dto;

import jakarta.validation.constraints.Min;

public record OrderItemUpdate(Long dishId, @Min(1) Integer quantity) {
}
