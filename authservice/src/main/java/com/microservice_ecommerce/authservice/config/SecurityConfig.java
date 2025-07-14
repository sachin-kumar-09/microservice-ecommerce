package com.microservice_ecommerce.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * PasswordEncoder bean used to hash user passwords securely using BCrypt.
     *
     * Why use BCrypt?
     * - Secure and widely used hash algorithm with salt built-in.
     * - Adaptive: can increase computational cost as hardware improves.
     *
     * Alternatives:
     * - `Pbkdf2PasswordEncoder` (good for FIPS compliance)
     * - `Argon2PasswordEncoder` (memory-hard, best for some modern apps)
     * - `NoOpPasswordEncoder` (only for testing — NOT secure)
     *
     * Why do we need this?
     * Spring Security requires a `PasswordEncoder` bean to verify hashed passwords during authentication.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
