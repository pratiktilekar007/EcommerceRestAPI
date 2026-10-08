package com.app.ecommerce.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
	
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
}
