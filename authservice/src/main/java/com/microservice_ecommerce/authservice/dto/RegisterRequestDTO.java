package com.microservice_ecommerce.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for capturing user registration input.
 *
 * Why use a DTO?
 * - Avoids exposing internal entity fields directly
 * - Allows precise validation and input control
 */
@Getter
@Setter
public class RegisterRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
