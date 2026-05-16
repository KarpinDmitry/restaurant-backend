package ru.karpin.restaurant.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karpin.restaurant.client.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
