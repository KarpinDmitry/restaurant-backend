package ru.karpin.restaurant.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.avatar.entity.Avatar;
import ru.karpin.restaurant.avatar.service.AvatarService;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.employee.dto.EmployeeCreate;
import ru.karpin.restaurant.employee.dto.EmployeePut;
import ru.karpin.restaurant.employee.dto.EmployeeResponse;
import ru.karpin.restaurant.employee.dto.EmployeeUpdate;
import ru.karpin.restaurant.employee.entity.Employee;
import ru.karpin.restaurant.employee.mapper.EmployeeMapper;
import ru.karpin.restaurant.employee.repository.EmployeeRepository;
import ru.karpin.restaurant.role.entity.Role;
import ru.karpin.restaurant.role.service.RoleService;
import ru.karpin.restaurant.user.entity.User;
import ru.karpin.restaurant.user.entity.UserType;
import ru.karpin.restaurant.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final RoleService roleService;
    private final AvatarService avatarService;

    @Transactional(readOnly = true)
    public ListResponse<EmployeeResponse> findAll(Long roleId, Boolean isWorking){
        List<Employee> employeeList;
        if (roleId != null && isWorking != null){
            employeeList = employeeRepository.findByRole_IdAndIsWorking(roleId, isWorking);
        } else if (roleId != null) {
            employeeList = employeeRepository.findByRole_Id(roleId);
        } else if (isWorking != null) {
            employeeList = employeeRepository.findByIsWorking(isWorking);
        } else {
            employeeList = employeeRepository.findAll();
        }

        return ListResponse.of(employeeList.stream()
                .map(EmployeeMapper::toResponse).toList());
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return EmployeeMapper.toResponse(employee);
    }

    public EmployeeResponse create(EmployeeCreate employeeCreate){
        User user = userService.create(employeeCreate.login(), employeeCreate.password(), UserType.EMPLOYEE);

        Role role = roleService.findEntityById(employeeCreate.roleId());
        Avatar avatar = employeeCreate.avatarId() != null
                ? avatarService.findEntityById(employeeCreate.avatarId()) : null;
        Employee employee = EmployeeMapper.toEmployee(employeeCreate, user, role, avatar);

        return EmployeeMapper.toResponse(employeeRepository.save(employee));
    }

    public EmployeeResponse update(EmployeePut employeePut, Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Role role = roleService.findEntityById(employeePut.roleId());
        Avatar avatar = employeePut.avatarId() != null
                ? avatarService.findEntityById(employeePut.avatarId()) : null;
        EmployeeMapper.applyPut(employee, employeePut, role, avatar);

        if (employeePut.password() != null){
            userService.updatePassword(id, employeePut.password());
        }

        return EmployeeMapper.toResponse(employee);
    }

    public EmployeeResponse patch(EmployeeUpdate employeeUpdate, Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Role role = employeeUpdate.roleId() != null
                ? roleService.findEntityById(employeeUpdate.roleId()) : null;
        Avatar avatar = employeeUpdate.avatarId() != null
                ? avatarService.findEntityById(employeeUpdate.avatarId()) : null;
        EmployeeMapper.applyPatch(employee, employeeUpdate, role, avatar);

        if (employeeUpdate.password() != null){
            userService.updatePassword(id, employeeUpdate.password());
        }

        return EmployeeMapper.toResponse(employee);
    }

    public void delete(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employeeRepository.delete(employee);
        userService.delete(id);
    }
}
