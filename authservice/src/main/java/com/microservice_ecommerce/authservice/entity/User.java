package com.microservice_ecommerce.authservice.entity;

import com.microservice_ecommerce.commonlib.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * The User entity represents an application user.
     *
     * Why only keep email?
     * - Email is treated as the unique identifier (username) in your business logic
     * - Simplifies authentication and avoids field duplication
     *
     * Why eager fetch on roles?
     * - Spring Security needs immediate access to authorities when authenticating
     */
}
