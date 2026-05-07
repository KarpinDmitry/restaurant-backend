package ru.karpin.restaurant.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String error,
        String exception,
        String message,
        Map<String, Object> details,
        OffsetDateTime timestamp
) {

    public static ErrorResponse of(String error, String exception, String message) {
        return new ErrorResponse(error, exception, message, null, OffsetDateTime.now());
    }
}
