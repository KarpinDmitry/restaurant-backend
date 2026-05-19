package ru.karpin.restaurant.orderstatus.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.orderstatus.dto.OrderStatusResponse;
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
}
