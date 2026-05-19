package ru.karpin.restaurant.employee.mapper;

import lombok.experimental.UtilityClass;
import ru.karpin.restaurant.user.entity.User;
import ru.karpin.restaurant.avatar.entity.Avatar;
import ru.karpin.restaurant.employee.dto.EmployeeCreate;
import ru.karpin.restaurant.employee.dto.EmployeePut;
import ru.karpin.restaurant.employee.dto.EmployeeResponse;
import ru.karpin.restaurant.employee.dto.EmployeeUpdate;
import ru.karpin.restaurant.employee.entity.Employee;
import ru.karpin.restaurant.role.dto.RoleResponse;
import ru.karpin.restaurant.role.entity.Role;

@UtilityClass
public class EmployeeMapper {

    public static EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getUserId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getMiddleName(),
                employee.getUser().getLogin(),
                employee.getRole().getId(),
                new RoleResponse(employee.getRole().getId(), employee.getRole().getName()),
                employee.getPhone(),
                employee.getAvatar() != null ? employee.getAvatar().getId() : null,
                employee.isWorking()
        );
    }

    public static Employee toEmployee(EmployeeCreate request, User user, Role role, Avatar avatar) {
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setMiddleName(request.middleName());
        employee.setRole(role);
        employee.setPhone(request.phone());
        employee.setAvatar(avatar);
        employee.setWorking(request.isWorking());
        return employee;
    }

    public static void applyPut(Employee employee, EmployeePut request, Role role, Avatar avatar) {
        employee.getUser().setLogin(request.login());
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setMiddleName(request.middleName());
        employee.setRole(role);
        employee.setPhone(request.phone());
        employee.setAvatar(avatar);
        if (request.isWorking() != null) {
            employee.setWorking(request.isWorking());
        }
    }

    public static void applyPatch(Employee employee, EmployeeUpdate request, Role role, Avatar avatar) {
        if (request.login() != null) employee.getUser().setLogin(request.login());
        if (request.firstName() != null) employee.setFirstName(request.firstName());
        if (request.lastName() != null) employee.setLastName(request.lastName());
        if (request.middleName() != null) employee.setMiddleName(request.middleName());
        if (role != null) employee.setRole(role);
        if (request.phone() != null) employee.setPhone(request.phone());
        if (request.avatarId() != null) employee.setAvatar(avatar);
        if (request.isWorking() != null) employee.setWorking(request.isWorking());
    }
}
