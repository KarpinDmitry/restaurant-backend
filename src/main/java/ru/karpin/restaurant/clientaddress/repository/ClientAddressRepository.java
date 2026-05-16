package ru.karpin.restaurant.clientaddress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.clientaddress.entity.ClientAddress;

import java.util.List;
import java.util.Optional;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long> {

    List<ClientAddress> findByClient_Id(Long clientId);

    Optional<ClientAddress> findByIdAndClient_Id(Long id, Long clientId);
}
