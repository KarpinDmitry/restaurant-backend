package ru.karpin.restaurant.role.mapper;

import org.springframework.stereotype.Component;
import ru.karpin.restaurant.role.dto.RoleResponse;
import ru.karpin.restaurant.role.entity.Role;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role entity) {
        return new RoleResponse(entity.getId(), entity.getName());
    }
}
