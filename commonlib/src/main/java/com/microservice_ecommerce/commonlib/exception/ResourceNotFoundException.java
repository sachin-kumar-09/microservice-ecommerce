package com.microservice_ecommerce.commonlib.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forId(Long id, String resourceName) {
        return new ResourceNotFoundException(resourceName + " with ID " + id + " not found.");
    }
}

