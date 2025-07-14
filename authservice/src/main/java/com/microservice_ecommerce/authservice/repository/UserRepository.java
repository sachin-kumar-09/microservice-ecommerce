package com.microservice_ecommerce.authservice.repository;

import com.microservice_ecommerce.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /**
     * Used by authentication logic to retrieve user details by username.
     *
     * Why Optional?
     * - Prevents null pointer exceptions
     *
     * Alternatives:
     * - findByEmail or findById could also be used depending on auth strategy
     */
}
