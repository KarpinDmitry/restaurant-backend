package ru.karpin.restaurant.orderstatus.mapper;

import org.springframework.stereotype.Component;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusCreateRequest;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusResponse;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusUpdateRequest;
import ru.karpin.restaurant.orderstatus.entity.OrderStatus;

@Component
public class OrderStatusMapper {

    public OrderStatusResponse toResponse(OrderStatus entity) {
        return new OrderStatusResponse(entity.getId(), entity.getStatusName());
    }

    public OrderStatus toEntity(OrderStatusCreateRequest request) {
        OrderStatus entity = new OrderStatus();
        entity.setStatusName(request.statusName());
        return entity;
    }

    public void updateEntity(OrderStatus entity, OrderStatusUpdateRequest request) {
        entity.setStatusName(request.statusName());
    }
}
