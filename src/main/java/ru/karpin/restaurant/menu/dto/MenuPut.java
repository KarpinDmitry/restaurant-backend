package ru.karpin.restaurant.menu.dto;

import jakarta.validation.constraints.NotBlank;

public record MenuPut(@NotBlank String name, @NotBlank String seasonality, Boolean isActive) {
}
