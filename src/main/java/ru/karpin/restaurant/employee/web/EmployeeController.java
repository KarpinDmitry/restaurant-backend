package ru.karpin.restaurant.employee.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.employee.dto.EmployeeCreate;
import ru.karpin.restaurant.employee.dto.EmployeePut;
import ru.karpin.restaurant.employee.dto.EmployeeResponse;
import ru.karpin.restaurant.employee.dto.EmployeeUpdate;
import ru.karpin.restaurant.employee.service.EmployeeService;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ListResponse<EmployeeResponse>> get(
            @RequestParam(name = "role_id", required = false) Long roleId,
            @RequestParam(name = "is_working", required = false) Boolean isWorking){

        ListResponse<EmployeeResponse> employeeResponseList = employeeService.findAll(roleId, isWorking);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id){
        EmployeeResponse employeeResponse = employeeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid EmployeeCreate employeeCreate){
        EmployeeResponse employeeResponse = employeeService.create(employeeCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@RequestBody @Valid EmployeePut employeePut,
                                                   @PathVariable Long id){
        EmployeeResponse employeeResponse = employeeService.update(employeePut, id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponse> patch(@RequestBody @Valid EmployeeUpdate employeeUpdate,
                                                   @PathVariable Long id){
        EmployeeResponse employeeResponse = employeeService.patch(employeeUpdate, id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        employeeService.delete(id);
    }
}
