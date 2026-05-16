package ru.karpin.restaurant.employee.dto;

import ru.karpin.restaurant.role.dto.RoleResponse;

public record EmployeeResponse(Long id, String firstName, String lastName, String middleName, String login,
                               Long roleId, RoleResponse role, String phone, Long avatarId, boolean isWorking) {
}
