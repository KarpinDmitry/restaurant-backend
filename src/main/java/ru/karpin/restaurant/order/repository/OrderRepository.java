package ru.karpin.restaurant.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.order.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClient_Id(Long clientId);

    List<Order> findByCourier_Id(Long courierId);
}
