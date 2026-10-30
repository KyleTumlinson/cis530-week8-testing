package edu.bellevue.cis530.week8.exception;

// Custom exception for missing resources in database
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
