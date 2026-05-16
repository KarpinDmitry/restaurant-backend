package ru.karpin.restaurant.menu.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.dish.dto.DishResponse;
import ru.karpin.restaurant.menu.dto.*;
import ru.karpin.restaurant.menu.service.MenuService;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<ListResponse<MenuResponse>> get(
            @RequestParam(name = "is_active", required = false) Boolean isActive) {
        return ResponseEntity.ok(menuService.findAll(isActive));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MenuResponse> create(@RequestBody @Valid MenuCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponse> update(@RequestBody @Valid MenuPut request,
                                               @PathVariable Long id) {
        return ResponseEntity.ok(menuService.update(request, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuResponse> patch(@RequestBody @Valid MenuUpdate request,
                                              @PathVariable Long id) {
        return ResponseEntity.ok(menuService.patch(request, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }

    @GetMapping("/{menuId}/dishes")
    public ResponseEntity<ListResponse<DishResponse>> getDishes(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.findDishes(menuId));
    }

    @PostMapping("/{menuId}/dishes")
    public ResponseEntity<MenuDishResponse> addDish(@PathVariable Long menuId,
                                                    @RequestBody @Valid MenuDishCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.addDish(menuId, request));
    }

    @DeleteMapping("/{menuId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDish(@PathVariable Long menuId, @PathVariable Long dishId) {
        menuService.removeDish(menuId, dishId);
    }
}
