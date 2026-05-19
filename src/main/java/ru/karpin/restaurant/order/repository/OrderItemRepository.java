package ru.karpin.restaurant.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.order.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder_Id(Long orderId);

    Optional<OrderItem> findByIdAndOrder_Id(Long id, Long orderId);
}
