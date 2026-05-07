package ru.karpin.restaurant.orderstatus.mapper;

import org.springframework.stereotype.Component;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusResponse;
import ru.karpin.restaurant.orderstatus.entity.OrderStatus;

@Component
public class OrderStatusMapper {

    public OrderStatusResponse toResponse(OrderStatus entity) {
        return new OrderStatusResponse(entity.getId(), entity.getStatusName());
    }
}
