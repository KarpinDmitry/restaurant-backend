package ru.karpin.restaurant.client.dto;

public record ClientResponse(Long id, String firstName, String lastName, String middleName,
                             String login, String email, String phone, Long avatarId) {
}
