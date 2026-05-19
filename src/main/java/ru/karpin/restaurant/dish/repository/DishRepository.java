package ru.karpin.restaurant.dish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.dish.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
