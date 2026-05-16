package ru.karpin.restaurant.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.karpin.restaurant.dish.entity.Dish;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price_at_moment", precision = 10, scale = 2, nullable = false)
    private BigDecimal priceAtMoment;
}
