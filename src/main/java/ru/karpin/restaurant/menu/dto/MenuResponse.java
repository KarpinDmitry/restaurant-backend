package ru.karpin.restaurant.menu.dto;

public record MenuResponse(Long id, String name, String seasonality, boolean isActive) {
}
