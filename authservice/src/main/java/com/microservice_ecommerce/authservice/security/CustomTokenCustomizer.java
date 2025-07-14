package com.microservice_ecommerce.authservice.security;

import com.microservice_ecommerce.authservice.entity.User;
import com.microservice_ecommerce.authservice.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserService userService;

    public CustomTokenCustomizer(UserService userService) {
        this.userService = userService;
    }

    /**
     * Customizes JWT access tokens by injecting additional claims.
     *
     * Why implement OAuth2TokenCustomizer?
     * - Spring Authorization Server allows you to plug into token creation
     * - We use this to add user-specific metadata like roles and ID
     *
     * How it's used:
     * - Triggered automatically by Spring during token creation
     * - Works only for JWT-based access tokens
     */
//    @Override
//    public void customize(JwtEncodingContext context) {
//        if (context.getTokenType().getValue().equals("access_token")) {
//            Authentication principal = context.getPrincipal();
//            String username = principal.getName();
//
//            // Retrieve user from DB for additional data
//            User user = userService.findByEmail(username)
//                    .orElseThrow(() -> new RuntimeException("User not found during token generation"));
//
//            // Collect authorities/roles as strings
//            Set<String> roles = principal.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toSet());
//
//            // Add custom claims to the JWT
//            context.getClaims().claim("user_id", user.getId());
//            context.getClaims().claim("email", user.getEmail());
//            context.getClaims().claim("roles", roles);
//        }
//    }


    @Override
    public void customize(JwtEncodingContext context) {
        Authentication principal = context.getPrincipal();

        // Only run for password or refresh_token grant types
        if (context.getAuthorizationGrantType().equals(AuthorizationGrantType.CLIENT_CREDENTIALS)) {
            // Do NOT try to look up user
            return;
        }

        String username = principal.getName();

        User user = userService.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found during token generation"));

        context.getClaims().claim("user_id", user.getId().toString());
        context.getClaims().claim("email", user.getEmail());
    }

}
