package ru.karpin.restaurant.orderstatus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusResponse;
import ru.karpin.restaurant.orderstatus.mapper.OrderStatusMapper;
import ru.karpin.restaurant.orderstatus.repository.OrderStatusRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderStatusService {

    private final OrderStatusRepository repository;
    private final OrderStatusMapper mapper;

    public ListResponse<OrderStatusResponse> findAll() {
        List<OrderStatusResponse> data = repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    public OrderStatusResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus with id " + id + " not found"));
    }
}
