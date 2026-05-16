package ru.karpin.restaurant.client.mapper;

import lombok.experimental.UtilityClass;
import ru.karpin.restaurant.avatar.entity.Avatar;
import ru.karpin.restaurant.client.dto.ClientCreate;
import ru.karpin.restaurant.client.dto.ClientPut;
import ru.karpin.restaurant.client.dto.ClientResponse;
import ru.karpin.restaurant.client.dto.ClientUpdate;
import ru.karpin.restaurant.client.entity.Client;
import ru.karpin.restaurant.user.entity.User;

@UtilityClass
public class ClientMapper {

    public static ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getMiddleName(),
                client.getUser().getLogin(),
                client.getEmail(),
                client.getPhone(),
                client.getAvatar() != null ? client.getAvatar().getId() : null
        );
    }

    public static Client toClient(ClientCreate request, User user, Avatar avatar) {
        Client client = new Client();
        client.setUser(user);
        client.setFirstName(request.firstName());
        client.setLastName(request.lastName());
        client.setMiddleName(request.middleName());
        client.setEmail(request.email());
        client.setPhone(request.phone());
        client.setAvatar(avatar);
        return client;
    }

    public static void applyPut(Client client, ClientPut request, Avatar avatar) {
        client.getUser().setLogin(request.login());
        client.setFirstName(request.firstName());
        client.setLastName(request.lastName());
        client.setMiddleName(request.middleName());
        client.setEmail(request.email());
        client.setPhone(request.phone());
        client.setAvatar(avatar);
    }

    public static void applyPatch(Client client, ClientUpdate request, Avatar avatar) {
        if (request.login() != null) client.getUser().setLogin(request.login());
        if (request.firstName() != null) client.setFirstName(request.firstName());
        if (request.lastName() != null) client.setLastName(request.lastName());
        if (request.middleName() != null) client.setMiddleName(request.middleName());
        if (request.email() != null) client.setEmail(request.email());
        if (request.phone() != null) client.setPhone(request.phone());
        if (request.avatarId() != null) client.setAvatar(avatar);
    }
}
