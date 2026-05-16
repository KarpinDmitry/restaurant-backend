package ru.karpin.restaurant.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderStatusHistoryCreate(@NotNull Long statusId, String comment) {
}
