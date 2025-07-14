package com.microservice_ecommerce.authservice.entity;

import com.microservice_ecommerce.commonlib.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The Role entity defines a user's authority in the system.
     *
     * Why use a class instead of enum?
     * - Role data can be dynamic and stored in DB
     * - Avoids code changes and redeployment when adding new roles
     *
     * How it's used:
     * - This will be mapped in a many-to-many relation with User
     * - Used by Spring Security during authentication/authorization
     */
}
