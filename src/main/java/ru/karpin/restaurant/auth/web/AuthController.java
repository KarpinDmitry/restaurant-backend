package ru.karpin.restaurant.auth.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.karpin.restaurant.auth.dto.LoginRequest;
import ru.karpin.restaurant.auth.dto.TokenResponse;
import ru.karpin.restaurant.auth.service.AuthService;
import ru.karpin.restaurant.client.dto.ClientCreate;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/employees/login")
    public TokenResponse loginEmployee(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/auth/employees/refresh")
    public TokenResponse refreshEmployee(@RequestHeader("Authorization") String authHeader) {
        return authService.refresh(extractToken(authHeader));
    }

    @PostMapping("/auth/clients/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse registerClient(@RequestBody @Valid ClientCreate request) {
        return authService.register(request);
    }

    @PostMapping("/auth/clients/login")
    public TokenResponse loginClient(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/auth/clients/refresh")
    public TokenResponse refreshClient(@RequestHeader("Authorization") String authHeader) {
        return authService.refresh(extractToken(authHeader));
    }

    private String extractToken(String authHeader) {
        return authHeader.substring(7);
    }
}
