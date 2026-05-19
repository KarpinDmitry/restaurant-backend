package ru.karpin.restaurant.ingredient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.DuplicateResourceException;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.ingredient.dto.IngredientCreateRequest;
import ru.karpin.restaurant.ingredient.dto.IngredientResponse;
import ru.karpin.restaurant.ingredient.dto.IngredientUpdateRequest;
import ru.karpin.restaurant.ingredient.entity.Ingredient;
import ru.karpin.restaurant.ingredient.mapper.IngredientMapper;
import ru.karpin.restaurant.ingredient.repository.IngredientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {

    private final IngredientRepository repository;
    private final IngredientMapper mapper;

    @Transactional(readOnly = true)
    public ListResponse<IngredientResponse> findAll() {
        List<IngredientResponse> data = repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    @Transactional(readOnly = true)
    public IngredientResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public IngredientResponse create(IngredientCreateRequest request) {
        if (repository.existsByNameIgnoreCase(request.name())) {
            throw duplicate(request.name());
        }
        Ingredient saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    public IngredientResponse update(Long id, IngredientUpdateRequest request) {
        Ingredient entity = repository.findById(id).orElseThrow(() -> notFound(id));
        if (repository.existsByNameIgnoreCaseAndIdNot(request.name(), id)) {
            throw duplicate(request.name());
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw notFound(id);
        }
        repository.deleteById(id);
    }

    private ResourceNotFoundException notFound(Long id) {
        return new ResourceNotFoundException("Ingredient with id " + id + " not found");
    }

    private DuplicateResourceException duplicate(String name) {
        return new DuplicateResourceException("Ingredient with name '" + name + "' already exists");
    }
}
