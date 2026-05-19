package ru.karpin.restaurant.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.karpin.restaurant.auth.filter.JwtAuthFilter;
import ru.karpin.restaurant.auth.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImp userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()

                        // Roles & order statuses — admin only
                        .requestMatchers("/roles/**").hasAuthority("ADMIN")
                        .requestMatchers("/order-statuses/**").hasAuthority("ADMIN")

                        // Employees
                        .requestMatchers(HttpMethod.GET, "/employees/**").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/employees/**").hasAuthority("ADMIN")

                        // Clients
                        .requestMatchers(HttpMethod.DELETE, "/clients/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/clients/**").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/clients/**").hasAnyAuthority("ADMIN", "MANAGER", "CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/clients/**").hasAnyAuthority("ADMIN", "MANAGER", "CLIENT")
                        .requestMatchers(HttpMethod.PATCH, "/clients/**").hasAnyAuthority("ADMIN", "MANAGER", "CLIENT")

                        // Dishes, menus, ingredients, photos, avatars
                        .requestMatchers(HttpMethod.GET, "/dishes/**").authenticated()
                        .requestMatchers("/dishes/**").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/menus/**").authenticated()
                        .requestMatchers("/menus/**").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/ingredients/**").authenticated()
                        .requestMatchers("/ingredients/**").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/photos/**").authenticated()
                        .requestMatchers("/photos/**").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/avatars/**").authenticated()
                        .requestMatchers("/avatars/**").hasAnyAuthority("ADMIN", "MANAGER")

                        // Orders
                        .requestMatchers(HttpMethod.POST, "/orders").hasAnyAuthority("ADMIN", "MANAGER", "CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/orders/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/orders/**").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/orders/**").hasAnyAuthority("ADMIN", "MANAGER", "COURIER", "CLIENT")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
