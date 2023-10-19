package com.example.searchservice.error;

public class InvalidExternalResponseException extends RuntimeException {

    public InvalidExternalResponseException(String message) {
        super(message);
    }
}
