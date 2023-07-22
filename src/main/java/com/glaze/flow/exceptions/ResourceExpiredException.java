package com.glaze.flow.exceptions;

public class ResourceExpiredException extends RuntimeException {

    public ResourceExpiredException(String message) {
        super(message);
    }
}
