package com.glaze.flow.exceptions;

public class ResourceNotFoundException extends LocalizedRuntimeException {

    public ResourceNotFoundException(String messageKey) {
        super(messageKey);
    }

    public ResourceNotFoundException(String messageKey, Object... objects) {
        super(messageKey, objects);
    }

}
