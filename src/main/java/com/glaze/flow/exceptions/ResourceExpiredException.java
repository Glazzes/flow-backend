package com.glaze.flow.exceptions;

public class ResourceExpiredException extends LocalizedRuntimeException {

    public ResourceExpiredException(String messageKey) {
        super(messageKey);
    }

    public ResourceExpiredException(String messageKey, Object... objects) {
        super(messageKey, objects);
    }
}
