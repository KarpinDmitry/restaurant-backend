package ru.karpin.restaurant.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.karpin.restaurant.auth.entity.UserPrincipal;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expiration;
    private final long refreshExpiration;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expiration,
                      @Value("${jwt.refresh-expiration}") long refreshExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateToken(UserPrincipal principal) {
        List<String> authorities = principal.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        return buildToken(principal.getUsername(), authorities, expiration, "access");
    }

    public String generateRefreshToken(UserPrincipal principal) {
        return buildToken(principal.getUsername(), List.of(), refreshExpiration, "refresh");
    }

    private String buildToken(String username, List<String> authorities, long ttl, String type) {
        return Jwts.builder()
                .subject(username)
                .claim("authorities", authorities)
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ttl))
                .signWith(secretKey)
                .compact();
    }

    public boolean isRefreshToken(String token) {
        return "refresh".equals(parseClaims(token).get("type", String.class));
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractAuthorities(String token) {
        return (List<String>) parseClaims(token).get("authorities");
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
