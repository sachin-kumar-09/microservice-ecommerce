package com.microservice_ecommerce.authservice.controller;

import com.microservice_ecommerce.authservice.dto.RegisterRequestDTO;
import com.microservice_ecommerce.authservice.entity.Role;
import com.microservice_ecommerce.authservice.entity.User;
import com.microservice_ecommerce.authservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user account based on email and password.
     *
     * Why use POST?
     * - Registration modifies server state and may be intercepted by validations
     *
     * Why use DTO?
     * - Input separation improves API clarity and validation
     *
     * Why return ResponseEntity?
     * - Allows flexible HTTP status handling
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequestDTO request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setEmail(request.getEmail()); // Use email as username
        user.setPassword(request.getPassword());

        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER"); // Default role

        user.setRoles(Collections.singleton(defaultRole));

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
