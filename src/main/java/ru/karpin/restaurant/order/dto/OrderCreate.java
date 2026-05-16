package ru.karpin.restaurant.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreate(@NotNull Long addressId, @NotEmpty @Valid List<OrderItemRequest> items) {
}
