package edu.bellevue.cis530.week8.exception;

import java.time.LocalDateTime;

// Error response is just a record that will hold the fields for the responseentity to display
public record ErrorResponse(String message, int status, LocalDateTime timestamp) {
}
