package ru.karpin.restaurant.order.dto;

import java.math.BigDecimal;

public record OrderItemResponse(Long id, Long orderId, Long dishId, Integer quantity, BigDecimal priceAtMoment) {
}
