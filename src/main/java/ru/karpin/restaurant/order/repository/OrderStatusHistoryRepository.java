package ru.karpin.restaurant.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.order.entity.OrderStatusHistory;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {

    List<OrderStatusHistory> findByOrder_IdOrderByChangedAtAsc(Long orderId);
}
