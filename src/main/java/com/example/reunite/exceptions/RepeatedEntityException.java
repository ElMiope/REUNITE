package com.example.reunite.exceptions;

public class RepeatedEntityException extends RuntimeException {
    public RepeatedEntityException(String message) {
        super(message);
    }
}
