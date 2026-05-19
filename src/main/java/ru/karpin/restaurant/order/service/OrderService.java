package ru.karpin.restaurant.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.clientaddress.entity.ClientAddress;
import ru.karpin.restaurant.clientaddress.repository.ClientAddressRepository;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.dish.entity.Dish;
import ru.karpin.restaurant.dish.repository.DishRepository;
import ru.karpin.restaurant.employee.entity.Employee;
import ru.karpin.restaurant.employee.repository.EmployeeRepository;
import ru.karpin.restaurant.order.dto.*;
import ru.karpin.restaurant.order.entity.Order;
import ru.karpin.restaurant.order.entity.OrderItem;
import ru.karpin.restaurant.order.entity.OrderStatusHistory;
import ru.karpin.restaurant.order.repository.OrderItemRepository;
import ru.karpin.restaurant.order.repository.OrderRepository;
import ru.karpin.restaurant.order.repository.OrderStatusHistoryRepository;
import ru.karpin.restaurant.orderstatus.entity.OrderStatus;
import ru.karpin.restaurant.orderstatus.repository.OrderStatusRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository statusHistoryRepository;
    private final ClientAddressRepository addressRepository;
    private final DishRepository dishRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Transactional(readOnly = true)
    public ListResponse<OrderResponse> findAll(Long clientId, Long courierId) {
        List<Order> orders;
        if (clientId != null) {
            orders = orderRepository.findByClient_Id(clientId);
        } else if (courierId != null) {
            orders = orderRepository.findByCourier_UserId(courierId);
        } else {
            orders = orderRepository.findAll();
        }
        return ListResponse.of(orders.stream().map(this::toResponse).toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        return toResponse(loadOrder(id));
    }

    public OrderResponse create(OrderCreate request) {
        ClientAddress address = addressRepository.findById(request.addressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found: " + request.addressId()));

        Order order = new Order();
        order.setClient(address.getClient());
        order.setAddress(address);
        order = orderRepository.save(order);

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.items()) {
            Dish dish = dishRepository.findById(itemReq.dishId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dish not found: " + itemReq.dishId()));
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setDish(dish);
            item.setQuantity(itemReq.quantity());
            item.setPriceAtMoment(dish.getPrice());
            items.add(item);
            total = total.add(dish.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())));
        }
        orderItemRepository.saveAll(items);

        order.setTotalPrice(total);

        OrderStatus newStatus = orderStatusRepository.findByStatusName("NEW")
                .orElseThrow(() -> new ResourceNotFoundException("Status NEW not found"));
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(newStatus);
        statusHistoryRepository.save(history);

        return toResponse(order);
    }

    public OrderResponse update(OrderUpdate request, Long id) {
        Order order = loadOrder(id);
        applyUpdate(order, request);
        return toResponse(order);
    }

    public OrderResponse patch(OrderUpdate request, Long id) {
        Order order = loadOrder(id);
        if (request.addressId() != null) {
            order.setAddress(addressRepository.findById(request.addressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found: " + request.addressId())));
        }
        if (request.managerId() != null) {
            order.setManager(loadEmployee(request.managerId()));
        }
        if (request.courierId() != null) {
            order.setCourier(loadEmployee(request.courierId()));
        }
        return toResponse(order);
    }

    public void delete(Long id) {
        orderRepository.delete(loadOrder(id));
    }

    @Transactional(readOnly = true)
    public ListResponse<OrderStatusHistoryResponse> findStatusHistory(Long orderId) {
        loadOrder(orderId);
        List<OrderStatusHistoryResponse> data = statusHistoryRepository
                .findByOrder_IdOrderByChangedAtAsc(orderId).stream()
                .map(this::toHistoryResponse)
                .toList();
        return ListResponse.of(data);
    }

    public OrderStatusHistoryResponse addStatusHistory(Long orderId, OrderStatusHistoryCreate request) {
        Order order = loadOrder(orderId);
        OrderStatus status = orderStatusRepository.findById(request.statusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + request.statusId()));
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(status);
        history.setComment(request.comment());
        return toHistoryResponse(statusHistoryRepository.save(history));
    }

    private void applyUpdate(Order order, OrderUpdate request) {
        if (request.addressId() != null) {
            order.setAddress(addressRepository.findById(request.addressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found: " + request.addressId())));
        }
        order.setManager(request.managerId() != null ? loadEmployee(request.managerId()) : null);
        order.setCourier(request.courierId() != null ? loadEmployee(request.courierId()) : null);
    }

    private Employee loadEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    private Order loadOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getClient().getId(),
                order.getAddress().getId(),
                order.getManager() != null ? order.getManager().getUserId() : null,
                order.getCourier() != null ? order.getCourier().getUserId() : null,
                order.getTotalPrice(),
                order.getCreatedAt()
        );
    }

    private OrderStatusHistoryResponse toHistoryResponse(OrderStatusHistory h) {
        return new OrderStatusHistoryResponse(
                h.getId(),
                h.getOrder().getId(),
                h.getStatus().getId(),
                h.getEmployee() != null ? h.getEmployee().getUserId() : null,
                h.getClient() != null ? h.getClient().getId() : null,
                h.getComment(),
                h.getChangedAt()
        );
    }
}
