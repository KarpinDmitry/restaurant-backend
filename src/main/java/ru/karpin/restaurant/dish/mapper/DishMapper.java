package ru.karpin.restaurant.dish.mapper;

import org.springframework.stereotype.Component;
import ru.karpin.restaurant.dish.dto.DishCreateRequest;
import ru.karpin.restaurant.dish.dto.DishResponse;
import ru.karpin.restaurant.dish.dto.DishUpdateRequest;
import ru.karpin.restaurant.dish.entity.Dish;

@Component
public class DishMapper {

    public DishResponse toResponse(Dish entity) {
        return new DishResponse(
                entity.getId(),
                entity.getName(),
                entity.getWeight(),
                entity.getCalories(),
                entity.getPrice(),
                entity.getDescription()
        );
    }

    public Dish toEntity(DishCreateRequest request) {
        Dish entity = new Dish();
        entity.setName(request.name());
        entity.setWeight(request.weight());
        entity.setCalories(request.calories());
        entity.setPrice(request.price());
        entity.setDescription(request.description());
        return entity;
    }

    public void updateEntity(Dish entity, DishUpdateRequest request) {
        if (request.name() != null) entity.setName(request.name());
        if (request.weight() != null) entity.setWeight(request.weight());
        if (request.calories() != null) entity.setCalories(request.calories());
        if (request.price() != null) entity.setPrice(request.price());
        if (request.description() != null) entity.setDescription(request.description());
    }
}
