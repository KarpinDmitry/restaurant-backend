package ru.karpin.restaurant.order.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.order.dto.OrderItemCreate;
import ru.karpin.restaurant.order.dto.OrderItemResponse;
import ru.karpin.restaurant.order.dto.OrderItemUpdate;
import ru.karpin.restaurant.order.service.OrderItemService;

@RestController
@RequestMapping("/orders/{orderId}/items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<ListResponse<OrderItemResponse>> get(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.findAll(orderId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getById(@PathVariable Long orderId,
                                                     @PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.findById(orderId, id));
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> create(@PathVariable Long orderId,
                                                    @RequestBody @Valid OrderItemCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.create(orderId, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderItemResponse> update(@PathVariable Long orderId,
                                                    @PathVariable Long id,
                                                    @RequestBody @Valid OrderItemUpdate request) {
        return ResponseEntity.ok(orderItemService.update(orderId, id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long orderId, @PathVariable Long id) {
        orderItemService.delete(orderId, id);
    }
}
