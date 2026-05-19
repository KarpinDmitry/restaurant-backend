package ru.karpin.restaurant.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeePut(@NotBlank String firstName, @NotBlank String lastName, String middleName,
                          @NotBlank @Size(min=4, max=16) String login, String password, @NotNull Long roleId,
                          @NotBlank String phone, Long avatarId, Boolean isWorking) {
}
