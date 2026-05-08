package ru.karpin.restaurant.orderstatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.orderstatus.entity.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
