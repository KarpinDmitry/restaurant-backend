package ru.karpin.restaurant.employee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.karpin.restaurant.user.entity.User;
import ru.karpin.restaurant.avatar.entity.Avatar;
import ru.karpin.restaurant.role.entity.Role;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    private String phone;

    @JoinColumn(name = "avatar_id")
    @ManyToOne
    private Avatar avatar;

    @Column(name = "is_working", nullable = false)
    private boolean isWorking = false;
}
