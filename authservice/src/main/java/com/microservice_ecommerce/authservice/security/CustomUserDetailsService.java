package com.microservice_ecommerce.authservice.security;

import com.microservice_ecommerce.authservice.entity.Role;
import com.microservice_ecommerce.authservice.entity.User;
import com.microservice_ecommerce.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * Loads user by username and converts domain User to Spring Security's UserDetails.
     * <p>
     * Why override this method?
     * - Spring Security uses it during authentication when using password grant type
     * - It expects a UserDetails object to validate credentials and check authorities
     * <p>
     * Why throw UsernameNotFoundException?
     * - Spring uses this to reject authentication if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
