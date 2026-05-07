package ru.karpin.restaurant.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.role.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
