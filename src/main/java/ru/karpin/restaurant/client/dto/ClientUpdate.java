package ru.karpin.restaurant.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ClientUpdate(String firstName, String lastName, String middleName,
                           @Size(min = 4, max = 16) String login, String password,
                           @Email String email, String phone, Long avatarId) {
}
