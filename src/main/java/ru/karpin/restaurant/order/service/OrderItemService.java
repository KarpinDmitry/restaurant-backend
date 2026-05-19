package ru.karpin.restaurant.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.dish.entity.Dish;
import ru.karpin.restaurant.dish.repository.DishRepository;
import ru.karpin.restaurant.order.dto.OrderItemCreate;
import ru.karpin.restaurant.order.dto.OrderItemResponse;
import ru.karpin.restaurant.order.dto.OrderItemUpdate;
import ru.karpin.restaurant.order.entity.Order;
import ru.karpin.restaurant.order.entity.OrderItem;
import ru.karpin.restaurant.order.repository.OrderItemRepository;
import ru.karpin.restaurant.order.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    @Transactional(readOnly = true)
    public ListResponse<OrderItemResponse> findAll(Long orderId) {
        loadOrder(orderId);
        List<OrderItemResponse> data = orderItemRepository.findByOrder_Id(orderId).stream()
                .map(this::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    @Transactional(readOnly = true)
    public OrderItemResponse findById(Long orderId, Long id) {
        return toResponse(loadItem(orderId, id));
    }

    public OrderItemResponse create(Long orderId, OrderItemCreate request) {
        Order order = loadOrder(orderId);
        Dish dish = loadDish(request.dishId());
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setDish(dish);
        item.setQuantity(request.quantity());
        item.setPriceAtMoment(dish.getPrice());
        orderItemRepository.save(item);
        recalculateTotal(order);
        return toResponse(item);
    }

    public OrderItemResponse update(Long orderId, Long id, OrderItemUpdate request) {
        Order order = loadOrder(orderId);
        OrderItem item = loadItem(orderId, id);
        if (request.dishId() != null) {
            Dish dish = loadDish(request.dishId());
            item.setDish(dish);
            item.setPriceAtMoment(dish.getPrice());
        }
        if (request.quantity() != null) {
            item.setQuantity(request.quantity());
        }
        recalculateTotal(order);
        return toResponse(item);
    }

    public void delete(Long orderId, Long id) {
        Order order = loadOrder(orderId);
        OrderItem item = loadItem(orderId, id);
        orderItemRepository.delete(item);
        recalculateTotal(order);
    }

    private void recalculateTotal(Order order) {
        BigDecimal total = orderItemRepository.findByOrder_Id(order.getId()).stream()
                .map(i -> i.getPriceAtMoment().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(total);
    }

    private Order loadOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
    }

    private OrderItem loadItem(Long orderId, Long id) {
        return orderItemRepository.findByIdAndOrder_Id(id, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Item " + id + " not found in order " + orderId));
    }

    private Dish loadDish(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found: " + dishId));
    }

    private OrderItemResponse toResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getOrder().getId(),
                item.getDish().getId(),
                item.getQuantity(),
                item.getPriceAtMoment()
        );
    }
}
