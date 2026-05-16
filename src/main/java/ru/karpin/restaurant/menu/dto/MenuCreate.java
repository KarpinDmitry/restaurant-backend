package ru.karpin.restaurant.menu.dto;

import jakarta.validation.constraints.NotBlank;

public record MenuCreate(@NotBlank String name, @NotBlank String seasonality, boolean isActive) {
}
