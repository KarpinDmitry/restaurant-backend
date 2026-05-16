package ru.karpin.restaurant.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.karpin.restaurant.employee.entity.Employee;
import ru.karpin.restaurant.role.entity.Role;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByRole_IdAndIsWorking(Long roleId, boolean isWorking);

    List<Employee> findByRole_Id(Long roleId);

    List<Employee> findByIsWorking(boolean isWorking);
}
