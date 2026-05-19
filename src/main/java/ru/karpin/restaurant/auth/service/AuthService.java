package ru.karpin.restaurant.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.karpin.restaurant.auth.dto.LoginRequest;
import ru.karpin.restaurant.auth.dto.TokenResponse;
import ru.karpin.restaurant.auth.entity.UserPrincipal;
import ru.karpin.restaurant.client.dto.ClientCreate;
import ru.karpin.restaurant.client.dto.ClientResponse;
import ru.karpin.restaurant.client.service.ClientService;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImp userDetailsService;
    private final JwtService jwtService;
    private final ClientService clientService;

    @Value("${jwt.expiration}")
    private long expiration;

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password())
        );
        UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserByUsername(request.login());
        return buildTokenResponse(principal);
    }

    public TokenResponse register(ClientCreate request) {
        ClientResponse client = clientService.create(request);
        UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserByUsername(request.login());
        return buildTokenResponse(principal);
    }

    public TokenResponse refresh(String refreshToken) {
        if (!jwtService.isTokenValid(refreshToken) || !jwtService.isRefreshToken(refreshToken)) {
            throw new ResourceNotFoundException("Invalid refresh token");
        }
        String username = jwtService.extractUsername(refreshToken);
        UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserByUsername(username);
        return buildTokenResponse(principal);
    }

    private TokenResponse buildTokenResponse(UserPrincipal principal) {
        String accessToken = jwtService.generateToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);
        return TokenResponse.of(accessToken, refreshToken, expiration);
    }
}
