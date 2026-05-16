package ru.karpin.restaurant.order.dto;

import java.time.OffsetDateTime;

public record OrderStatusHistoryResponse(Long id, Long orderId, Long statusId,
                                          Long employeeId, Long clientId,
                                          String comment, OffsetDateTime changedAt) {
}
