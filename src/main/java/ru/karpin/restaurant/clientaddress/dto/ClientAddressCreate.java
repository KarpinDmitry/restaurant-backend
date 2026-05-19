package ru.karpin.restaurant.clientaddress.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientAddressCreate(@NotBlank String addressText) {
}
