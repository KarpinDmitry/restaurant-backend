package ru.karpin.restaurant.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.ingredient.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
