package ru.karpin.restaurant.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
