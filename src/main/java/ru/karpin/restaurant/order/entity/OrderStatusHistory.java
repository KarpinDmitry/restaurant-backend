package ru.karpin.restaurant.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.karpin.restaurant.client.entity.Client;
import ru.karpin.restaurant.employee.entity.Employee;
import ru.karpin.restaurant.orderstatus.entity.OrderStatus;

import java.time.OffsetDateTime;

@Entity
@Table(name = "order_status_history")
@Getter
@Setter
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "changed_at", nullable = false)
    private OffsetDateTime changedAt;

    @PrePersist
    void prePersist() {
        changedAt = OffsetDateTime.now();
    }
}
