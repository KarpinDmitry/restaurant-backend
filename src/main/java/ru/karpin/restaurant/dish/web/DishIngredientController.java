package ru.karpin.restaurant.dish.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.dish.dto.DishIngredientCreateRequest;
import ru.karpin.restaurant.dish.dto.DishIngredientResponse;
import ru.karpin.restaurant.dish.service.DishIngredientService;
import ru.karpin.restaurant.ingredient.dto.IngredientResponse;

@RestController
@RequestMapping("/dishes/{dishId}/ingredients")
@RequiredArgsConstructor
public class DishIngredientController {

    private final DishIngredientService service;

    @GetMapping
    public ListResponse<IngredientResponse> list(@PathVariable Long dishId) {
        return service.findIngredients(dishId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishIngredientResponse link(
            @PathVariable Long dishId,
            @Valid @RequestBody DishIngredientCreateRequest request
    ) {
        return service.link(dishId, request);
    }

    @DeleteMapping("/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlink(@PathVariable Long dishId, @PathVariable Long ingredientId) {
        service.unlink(dishId, ingredientId);
    }
}
