package ru.karpin.restaurant.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.avatar.entity.Avatar;
import ru.karpin.restaurant.avatar.service.AvatarService;
import ru.karpin.restaurant.client.dto.ClientCreate;
import ru.karpin.restaurant.client.dto.ClientPut;
import ru.karpin.restaurant.client.dto.ClientResponse;
import ru.karpin.restaurant.client.dto.ClientUpdate;
import ru.karpin.restaurant.client.entity.Client;
import ru.karpin.restaurant.client.mapper.ClientMapper;
import ru.karpin.restaurant.client.repository.ClientRepository;
import ru.karpin.restaurant.common.dto.ListResponse;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.user.entity.User;
import ru.karpin.restaurant.user.entity.UserType;
import ru.karpin.restaurant.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserService userService;
    private final AvatarService avatarService;

    @Transactional(readOnly = true)
    public ListResponse<ClientResponse> findAll() {
        List<ClientResponse> data = clientRepository.findAll().stream()
                .map(ClientMapper::toResponse)
                .toList();
        return ListResponse.of(data);
    }

    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return ClientMapper.toResponse(client);
    }

    public ClientResponse create(ClientCreate request) {
        User user = userService.create(request.login(), request.password(), UserType.CLIENT);
        Avatar avatar = request.avatarId() != null
                ? avatarService.findEntityById(request.avatarId()) : null;
        Client client = ClientMapper.toClient(request, user, avatar);
        return ClientMapper.toResponse(clientRepository.save(client));
    }

    public ClientResponse update(ClientPut request, Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        Avatar avatar = request.avatarId() != null
                ? avatarService.findEntityById(request.avatarId()) : null;
        ClientMapper.applyPut(client, request, avatar);
        if (request.password() != null) {
            userService.updatePassword(id, request.password());
        }
        return ClientMapper.toResponse(client);
    }

    public ClientResponse patch(ClientUpdate request, Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        Avatar avatar = request.avatarId() != null
                ? avatarService.findEntityById(request.avatarId()) : null;
        ClientMapper.applyPatch(client, request, avatar);
        if (request.password() != null) {
            userService.updatePassword(id, request.password());
        }
        return ClientMapper.toResponse(client);
    }

    public void delete(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        clientRepository.delete(client);
        userService.delete(id);
    }
}
