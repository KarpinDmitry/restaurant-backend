package ru.karpin.restaurant.dish.web;

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
import ru.karpin.restaurant.dish.dto.DishCreateRequest;
import ru.karpin.restaurant.dish.dto.DishResponse;
import ru.karpin.restaurant.dish.dto.DishUpdateRequest;
import ru.karpin.restaurant.dish.service.DishService;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService service;

    @GetMapping
    public ListResponse<DishResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DishResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishResponse create(@Valid @RequestBody DishCreateRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public DishResponse update(@PathVariable Long id, @Valid @RequestBody DishUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}")
    public DishResponse patch(@PathVariable Long id, @Valid @RequestBody DishUpdateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
