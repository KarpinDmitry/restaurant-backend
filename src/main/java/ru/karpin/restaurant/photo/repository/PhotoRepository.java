package ru.karpin.restaurant.photo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpin.restaurant.photo.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
