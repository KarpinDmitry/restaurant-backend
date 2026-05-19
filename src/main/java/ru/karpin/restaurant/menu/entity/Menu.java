package ru.karpin.restaurant.menu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.karpin.restaurant.dish.entity.Dish;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menus")
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private String seasonality;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu_dishes",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private Set<Dish> dishes = new HashSet<>();
}
