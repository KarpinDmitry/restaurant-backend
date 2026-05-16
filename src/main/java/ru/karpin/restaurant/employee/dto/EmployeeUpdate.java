package ru.karpin.restaurant.employee.dto;

import jakarta.validation.constraints.Size;

public record EmployeeUpdate(String firstName, String lastName, String middleName,
                             @Size(min=4, max=16) String login, String password, Long roleId,
                             String phone, Long avatarId, Boolean isWorking) {
}
