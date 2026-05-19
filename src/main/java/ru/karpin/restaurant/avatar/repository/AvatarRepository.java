package ru.karpin.restaurant.avatar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.avatar.entity.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
