package com.glaze.flow.exceptions;

public class ExpiredResourceException extends RuntimeException {

    public ExpiredResourceException(String message) {
        super(message);
    }
}
