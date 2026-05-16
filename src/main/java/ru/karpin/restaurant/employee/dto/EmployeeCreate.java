package ru.karpin.restaurant.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeeCreate(@NotBlank String firstName, @NotBlank String lastName, String middleName,
                             @NotBlank @Size(min=4, max=16) String login, @NotBlank String password,
                             @NotNull Long roleId, @NotBlank String phone, Long avatarId, boolean isWorking) {
}
