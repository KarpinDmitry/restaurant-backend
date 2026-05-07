package ru.karpin.restaurant.orderstatus.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusCreateRequest;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusResponse;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusUpdateRequest;
import ru.karpin.restaurant.orderstatus.service.OrderStatusService;

@RestController
@RequestMapping("/order-statuses")
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService service;

    @GetMapping
    public ListResponse<OrderStatusResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderStatusResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderStatusResponse create(@Valid @RequestBody OrderStatusCreateRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public OrderStatusResponse update(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}")
    public OrderStatusResponse patch(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
