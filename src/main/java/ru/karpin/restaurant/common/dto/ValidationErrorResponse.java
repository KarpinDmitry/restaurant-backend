package ru.karpin.restaurant.common.dto;

import java.util.List;
import java.util.Map;

public record ValidationErrorResponse(
        String error,
        String message,
        Map<String, List<String>> fields
) {
}
