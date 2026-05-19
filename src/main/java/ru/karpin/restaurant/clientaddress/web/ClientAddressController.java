package ru.karpin.restaurant.clientaddress.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karpin.restaurant.clientaddress.dto.ClientAddressCreate;
import ru.karpin.restaurant.clientaddress.dto.ClientAddressResponse;
import ru.karpin.restaurant.clientaddress.dto.ClientAddressUpdate;
import ru.karpin.restaurant.clientaddress.service.ClientAddressService;
import ru.karpin.restaurant.common.dto.ListResponse;

@RestController
@RequestMapping("/clients/{clientId}/addresses")
@RequiredArgsConstructor
public class ClientAddressController {

    private final ClientAddressService addressService;

    @GetMapping
    public ResponseEntity<ListResponse<ClientAddressResponse>> get(@PathVariable Long clientId) {
        return ResponseEntity.ok(addressService.findAll(clientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientAddressResponse> getById(@PathVariable Long clientId,
                                                         @PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(clientId, id));
    }

    @PostMapping
    public ResponseEntity<ClientAddressResponse> create(@PathVariable Long clientId,
                                                        @RequestBody @Valid ClientAddressCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(clientId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientAddressResponse> update(@PathVariable Long clientId,
                                                        @PathVariable Long id,
                                                        @RequestBody @Valid ClientAddressCreate request) {
        return ResponseEntity.ok(addressService.update(clientId, id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientAddressResponse> patch(@PathVariable Long clientId,
                                                       @PathVariable Long id,
                                                       @RequestBody @Valid ClientAddressUpdate request) {
        return ResponseEntity.ok(addressService.patch(clientId, id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long clientId, @PathVariable Long id) {
        addressService.delete(clientId, id);
    }
}
