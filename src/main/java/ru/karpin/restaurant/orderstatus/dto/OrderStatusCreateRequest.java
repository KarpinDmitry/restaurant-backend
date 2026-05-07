package ru.karpin.restaurant.orderstatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderStatusCreateRequest(
        @NotBlank
        @Size(max = 50)
        String statusName
) {
}
