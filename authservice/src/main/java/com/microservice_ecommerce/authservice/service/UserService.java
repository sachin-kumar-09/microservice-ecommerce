package com.microservice_ecommerce.authservice.service;

import com.microservice_ecommerce.authservice.entity.User;
import com.microservice_ecommerce.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Registers a new user with encoded password.
     *
     * Why encode the password?
     * - To prevent storing passwords in plaintext
     * - Required by Spring Security’s authentication mechanism
     */
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Fetch user by username for authentication and business logic.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
