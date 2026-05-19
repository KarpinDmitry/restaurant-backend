package ru.karpin.restaurant.clientaddress.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.client.entity.Client;
import ru.karpin.restaurant.client.repository.ClientRepository;
import ru.karpin.restaurant.clientaddress.dto.ClientAddressCreate;
import ru.karpin.restaurant.clientaddress.dto.ClientAddressResponse;
import ru.karpin.restaurant.clientaddress.dto.ClientAddressUpdate;
import ru.karpin.restaurant.clientaddress.entity.ClientAddress;
import ru.karpin.restaurant.clientaddress.repository.ClientAddressRepository;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientAddressService {

    private final ClientAddressRepository addressRepository;
    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public ListResponse<ClientAddressResponse> findAll(Long clientId) {
        ensureClientExists(clientId);
        List<ClientAddressResponse> data = addressRepository.findByClient_Id(clientId).stream()
                .map(this::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    @Transactional(readOnly = true)
    public ClientAddressResponse findById(Long clientId, Long id) {
        return toResponse(loadAddress(clientId, id));
    }

    public ClientAddressResponse create(Long clientId, ClientAddressCreate request) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + clientId));
        ClientAddress address = new ClientAddress();
        address.setClient(client);
        address.setAddressText(request.addressText());
        return toResponse(addressRepository.save(address));
    }

    public ClientAddressResponse update(Long clientId, Long id, ClientAddressCreate request) {
        ClientAddress address = loadAddress(clientId, id);
        address.setAddressText(request.addressText());
        return toResponse(address);
    }

    public ClientAddressResponse patch(Long clientId, Long id, ClientAddressUpdate request) {
        ClientAddress address = loadAddress(clientId, id);
        if (request.addressText() != null) address.setAddressText(request.addressText());
        return toResponse(address);
    }

    public void delete(Long clientId, Long id) {
        addressRepository.delete(loadAddress(clientId, id));
    }

    private ClientAddress loadAddress(Long clientId, Long id) {
        return addressRepository.findByIdAndClient_Id(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address " + id + " not found for client " + clientId));
    }

    private void ensureClientExists(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client not found: " + clientId);
        }
    }

    private ClientAddressResponse toResponse(ClientAddress address) {
        return new ClientAddressResponse(
                address.getId(),
                address.getClient().getId(),
                address.getAddressText()
        );
    }
}
