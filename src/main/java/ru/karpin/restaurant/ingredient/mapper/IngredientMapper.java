package ru.karpin.restaurant.ingredient.mapper;

import org.springframework.stereotype.Component;
import ru.karpin.restaurant.ingredient.dto.IngredientCreateRequest;
import ru.karpin.restaurant.ingredient.dto.IngredientResponse;
import ru.karpin.restaurant.ingredient.dto.IngredientUpdateRequest;
import ru.karpin.restaurant.ingredient.entity.Ingredient;

@Component
public class IngredientMapper {

    public IngredientResponse toResponse(Ingredient entity) {
        return new IngredientResponse(entity.getId(), entity.getName());
    }

    public Ingredient toEntity(IngredientCreateRequest request) {
        Ingredient entity = new Ingredient();
        entity.setName(request.name());
        return entity;
    }

    public void updateEntity(Ingredient entity, IngredientUpdateRequest request) {
        entity.setName(request.name());
    }
}
