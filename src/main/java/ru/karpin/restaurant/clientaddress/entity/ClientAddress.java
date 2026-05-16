package ru.karpin.restaurant.clientaddress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.karpin.restaurant.client.entity.Client;

@Entity
@Table(name = "client_addresses")
@Getter
@Setter
public class ClientAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "address_text", nullable = false, columnDefinition = "TEXT")
    private String addressText;
}
