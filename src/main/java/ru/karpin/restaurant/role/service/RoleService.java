package ru.karpin.restaurant.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.role.dto.RoleResponse;
import ru.karpin.restaurant.role.entity.Role;
import ru.karpin.restaurant.role.mapper.RoleMapper;
import ru.karpin.restaurant.role.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    public ListResponse<RoleResponse> findAll() {
        List<RoleResponse> data = repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    public Role findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));
    }

    public RoleResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }
}
