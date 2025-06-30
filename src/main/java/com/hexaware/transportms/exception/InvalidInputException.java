package com.hexaware.transportms.exception;

public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
    public InvalidInputException() {
        super("Invalid input.");
    }
}
