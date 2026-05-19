package ru.karpin.restaurant.order.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.order.dto.*;
import ru.karpin.restaurant.order.service.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ListResponse<OrderResponse>> get(
            @RequestParam(name = "client_id", required = false) Long clientId,
            @RequestParam(name = "courier_id", required = false) Long courierId) {
        return ResponseEntity.ok(orderService.findAll(clientId, courierId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid OrderCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@RequestBody @Valid OrderUpdate request,
                                                @PathVariable Long id) {
        return ResponseEntity.ok(orderService.update(request, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponse> patch(@RequestBody @Valid OrderUpdate request,
                                               @PathVariable Long id) {
        return ResponseEntity.ok(orderService.patch(request, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

    @GetMapping("/{orderId}/status-history")
    public ResponseEntity<ListResponse<OrderStatusHistoryResponse>> getStatusHistory(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findStatusHistory(orderId));
    }

    @PostMapping("/{orderId}/status-history")
    public ResponseEntity<OrderStatusHistoryResponse> addStatusHistory(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderStatusHistoryCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.addStatusHistory(orderId, request));
    }
}
