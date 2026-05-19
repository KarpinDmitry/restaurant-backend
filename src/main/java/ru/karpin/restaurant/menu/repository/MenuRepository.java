package ru.karpin.restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.menu.entity.Menu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByIsActive(boolean isActive);
}
