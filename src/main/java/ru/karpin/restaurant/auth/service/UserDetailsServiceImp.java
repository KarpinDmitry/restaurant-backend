package ru.karpin.restaurant.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.karpin.restaurant.auth.entity.UserPrincipal;
import ru.karpin.restaurant.employee.dto.EmployeeResponse;
import ru.karpin.restaurant.employee.service.EmployeeService;
import ru.karpin.restaurant.role.dto.RoleResponse;
import ru.karpin.restaurant.user.entity.User;
import ru.karpin.restaurant.user.entity.UserType;
import ru.karpin.restaurant.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserService userService;
    private final EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findEntityByLogin(username);

        List<GrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority(user.getUserType().name()));
        if (user.getUserType().equals(UserType.EMPLOYEE)){

            EmployeeResponse employee = employeeService.findById(user.getId());

            RoleResponse role = employee.role();

            authorityList.add(new SimpleGrantedAuthority(role.name()));
        }
        UserPrincipal userPrincipal = new UserPrincipal(user, authorityList);

        return userPrincipal;
    }
}
