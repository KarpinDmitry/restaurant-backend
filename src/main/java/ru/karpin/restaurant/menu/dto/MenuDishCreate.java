package ru.karpin.restaurant.menu.dto;

import jakarta.validation.constraints.NotNull;

public record MenuDishCreate(@NotNull Long dishId) {
}
