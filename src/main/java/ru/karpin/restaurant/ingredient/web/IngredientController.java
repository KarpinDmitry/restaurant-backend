package ru.karpin.restaurant.ingredient.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.ingredient.dto.IngredientCreateRequest;
import ru.karpin.restaurant.ingredient.dto.IngredientResponse;
import ru.karpin.restaurant.ingredient.dto.IngredientUpdateRequest;
import ru.karpin.restaurant.ingredient.service.IngredientService;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService service;

    @GetMapping
    public ListResponse<IngredientResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public IngredientResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponse create(@Valid @RequestBody IngredientCreateRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public IngredientResponse update(@PathVariable Long id, @Valid @RequestBody IngredientUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}")
    public IngredientResponse patch(@PathVariable Long id, @Valid @RequestBody IngredientUpdateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
