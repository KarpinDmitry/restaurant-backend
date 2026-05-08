package ru.karpin.restaurant.dish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.dish.dto.DishCreateRequest;
import ru.karpin.restaurant.dish.dto.DishResponse;
import ru.karpin.restaurant.dish.dto.DishUpdateRequest;
import ru.karpin.restaurant.dish.entity.Dish;
import ru.karpin.restaurant.dish.mapper.DishMapper;
import ru.karpin.restaurant.dish.repository.DishRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DishService {

    private final DishRepository repository;
    private final DishMapper mapper;

    @Transactional(readOnly = true)
    public ListResponse<DishResponse> findAll() {
        List<DishResponse> data = repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    @Transactional(readOnly = true)
    public DishResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public DishResponse create(DishCreateRequest request) {
        Dish saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    public DishResponse update(Long id, DishUpdateRequest request) {
        Dish entity = repository.findById(id).orElseThrow(() -> notFound(id));
        mapper.updateEntity(entity, request);
        return mapper.toResponse(entity);
    }

    public void delete(Long id) {
        Dish dish = repository.findById(id).orElseThrow(() -> notFound(id));
        // Detach all M:M associations so Hibernate clears join tables before deleting the dish.
        dish.getPhotos().clear();
        dish.getIngredients().clear();
        repository.delete(dish);
    }

    private ResourceNotFoundException notFound(Long id) {
        return new ResourceNotFoundException("Dish with id " + id + " not found");
    }
}
