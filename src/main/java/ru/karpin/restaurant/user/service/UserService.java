package ru.karpin.restaurant.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpin.restaurant.common.exception.ResourceNotFoundException;
import ru.karpin.restaurant.user.entity.User;
import ru.karpin.restaurant.user.entity.UserType;
import ru.karpin.restaurant.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String login, String password, UserType userType){
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setUserType(userType);

        return userRepository.save(newUser);
    }

    public User findEntityByLogin(String login){
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Не найден: " + login));
    }

    public User findEntityById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void updatePassword(Long userId, String password){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPasswordHash(passwordEncoder.encode(password));
    }

    public void delete(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }
}
