package ru.karpin.restaurant.dish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.dish.dto.DishIngredientCreateRequest;
import ru.karpin.restaurant.dish.dto.DishIngredientResponse;
import ru.karpin.restaurant.dish.entity.Dish;
import ru.karpin.restaurant.dish.repository.DishRepository;
import ru.karpin.restaurant.ingredient.dto.IngredientResponse;
import ru.karpin.restaurant.ingredient.entity.Ingredient;
import ru.karpin.restaurant.ingredient.mapper.IngredientMapper;
import ru.karpin.restaurant.ingredient.repository.IngredientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DishIngredientService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Transactional(readOnly = true)
    public ListResponse<IngredientResponse> findIngredients(Long dishId) {
        Dish dish = loadDish(dishId);
        List<IngredientResponse> data = dish.getIngredients().stream()
                .map(ingredientMapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    public DishIngredientResponse link(Long dishId, DishIngredientCreateRequest request) {
        Dish dish = loadDish(dishId);
        Ingredient ingredient = ingredientRepository.findById(request.ingredientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ingredient with id " + request.ingredientId() + " not found"));
        dish.getIngredients().add(ingredient);
        return new DishIngredientResponse(dishId, ingredient.getId());
    }

    public void unlink(Long dishId, Long ingredientId) {
        Dish dish = loadDish(dishId);
        boolean removed = dish.getIngredients().removeIf(i -> i.getId().equals(ingredientId));
        if (!removed) {
            throw new ResourceNotFoundException(
                    "Ingredient " + ingredientId + " is not linked to dish " + dishId);
        }
    }

    private Dish loadDish(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dish with id " + dishId + " not found"));
    }
}
