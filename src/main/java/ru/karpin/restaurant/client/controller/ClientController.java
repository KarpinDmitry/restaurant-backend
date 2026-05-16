package ru.karpin.restaurant.client.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karpin.restaurant.client.dto.ClientCreate;
import ru.karpin.restaurant.client.dto.ClientPut;
import ru.karpin.restaurant.client.dto.ClientResponse;
import ru.karpin.restaurant.client.dto.ClientUpdate;
import ru.karpin.restaurant.client.service.ClientService;
import ru.karpin.restaurant.common.dto.ListResponse;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<ListResponse<ClientResponse>> get() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid ClientCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@RequestBody @Valid ClientPut request,
                                                  @PathVariable Long id) {
        return ResponseEntity.ok(clientService.update(request, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> patch(@RequestBody @Valid ClientUpdate request,
                                                 @PathVariable Long id) {
        return ResponseEntity.ok(clientService.patch(request, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
