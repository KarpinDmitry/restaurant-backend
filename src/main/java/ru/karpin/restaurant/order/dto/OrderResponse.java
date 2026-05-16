package ru.karpin.restaurant.order.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record OrderResponse(Long id, Long clientId, Long addressId,
                             Long managerId, Long courierId,
                             BigDecimal totalPrice, OffsetDateTime createdAt) {
}
