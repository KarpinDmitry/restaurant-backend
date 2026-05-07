package ru.karpin.restaurant.orderstatus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.DuplicateResourceException;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusCreateRequest;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusResponse;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusUpdateRequest;
import ru.karpin.restaurant.orderstatus.entity.OrderStatus;
import ru.karpin.restaurant.orderstatus.mapper.OrderStatusMapper;
import ru.karpin.restaurant.orderstatus.repository.OrderStatusRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderStatusService {

    private final OrderStatusRepository repository;
    private final OrderStatusMapper mapper;

    @Transactional(readOnly = true)
    public ListResponse<OrderStatusResponse> findAll() {
        List<OrderStatusResponse> data = repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    @Transactional(readOnly = true)
    public OrderStatusResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public OrderStatusResponse create(OrderStatusCreateRequest request) {
        if (repository.existsByStatusNameIgnoreCase(request.statusName())) {
            throw duplicate(request.statusName());
        }
        OrderStatus saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    public OrderStatusResponse update(Long id, OrderStatusUpdateRequest request) {
        OrderStatus entity = repository.findById(id).orElseThrow(() -> notFound(id));
        if (repository.existsByStatusNameIgnoreCaseAndIdNot(request.statusName(), id)) {
            throw duplicate(request.statusName());
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
        return new ResourceNotFoundException("OrderStatus with id " + id + " not found");
    }

    private DuplicateResourceException duplicate(String statusName) {
        return new DuplicateResourceException("OrderStatus with status_name '" + statusName + "' already exists");
    }
}
