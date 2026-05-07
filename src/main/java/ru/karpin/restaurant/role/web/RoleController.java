package ru.karpin.restaurant.role.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.role.dto.RoleResponse;
import ru.karpin.restaurant.role.service.RoleService;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    public ListResponse<RoleResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RoleResponse get(@PathVariable Long id) {
        return service.findById(id);
    }
}
