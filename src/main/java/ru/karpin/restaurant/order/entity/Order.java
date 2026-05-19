package ru.karpin.restaurant.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.karpin.restaurant.client.entity.Client;
import ru.karpin.restaurant.clientaddress.entity.ClientAddress;
import ru.karpin.restaurant.employee.entity.Employee;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private ClientAddress address;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Employee courier;

    @Column(name = "total_price", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = OffsetDateTime.now();
    }
}
