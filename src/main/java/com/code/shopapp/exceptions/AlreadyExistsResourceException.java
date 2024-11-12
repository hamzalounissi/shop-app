package com.code.shopapp.exceptions;

public class AlreadyExistsResourceException extends RuntimeException {
    public AlreadyExistsResourceException(String message) {
        super(message);
    }
}
