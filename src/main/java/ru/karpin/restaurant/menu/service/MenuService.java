package ru.karpin.restaurant.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.dish.entity.Dish;
import ru.karpin.restaurant.dish.repository.DishRepository;
import ru.karpin.restaurant.dish.dto.DishResponse;
import ru.karpin.restaurant.dish.mapper.DishMapper;
import ru.karpin.restaurant.menu.dto.*;
import ru.karpin.restaurant.menu.entity.Menu;
import ru.karpin.restaurant.menu.mapper.MenuMapper;
import ru.karpin.restaurant.menu.repository.MenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Transactional(readOnly = true)
    public ListResponse<MenuResponse> findAll(Boolean isActive) {
        List<Menu> menus = isActive != null
                ? menuRepository.findByIsActive(isActive)
                : menuRepository.findAll();
        return ListResponse.of(menus.stream().map(MenuMapper::toResponse).toList());
    }

    @Transactional(readOnly = true)
    public MenuResponse findById(Long id) {
        return MenuMapper.toResponse(loadMenu(id));
    }

    public MenuResponse create(MenuCreate request) {
        Menu menu = MenuMapper.toMenu(request);
        return MenuMapper.toResponse(menuRepository.save(menu));
    }

    public MenuResponse update(MenuPut request, Long id) {
        Menu menu = loadMenu(id);
        menu.setName(request.name());
        menu.setSeasonality(request.seasonality());
        if (request.isActive() != null) menu.setActive(request.isActive());
        return MenuMapper.toResponse(menu);
    }

    public MenuResponse patch(MenuUpdate request, Long id) {
        Menu menu = loadMenu(id);
        if (request.name() != null) menu.setName(request.name());
        if (request.seasonality() != null) menu.setSeasonality(request.seasonality());
        if (request.isActive() != null) menu.setActive(request.isActive());
        return MenuMapper.toResponse(menu);
    }

    public void delete(Long id) {
        menuRepository.delete(loadMenu(id));
    }

    @Transactional(readOnly = true)
    public ListResponse<DishResponse> findDishes(Long menuId) {
        Menu menu = loadMenu(menuId);
        List<DishResponse> data = menu.getDishes().stream()
                .map(dishMapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    public MenuDishResponse addDish(Long menuId, MenuDishCreate request) {
        Menu menu = loadMenu(menuId);
        Dish dish = dishRepository.findById(request.dishId())
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found: " + request.dishId()));
        menu.getDishes().add(dish);
        return new MenuDishResponse(menuId, dish.getId());
    }

    public void removeDish(Long menuId, Long dishId) {
        Menu menu = loadMenu(menuId);
        boolean removed = menu.getDishes().removeIf(d -> d.getId().equals(dishId));
        if (!removed) {
            throw new ResourceNotFoundException("Dish " + dishId + " is not in menu " + menuId);
        }
    }

    private Menu loadMenu(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found: " + id));
    }
}
