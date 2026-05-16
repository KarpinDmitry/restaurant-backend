package ru.karpin.restaurant.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientCreate(@NotBlank String firstName, @NotBlank String lastName, String middleName,
                           @NotBlank @Size(min = 4, max = 16) String login, @NotBlank String password,
                           @NotBlank @Email String email, @NotBlank String phone, Long avatarId) {
}
