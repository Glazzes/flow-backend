package com.glaze.flow.exceptions;

public class LocalizedRuntimeException extends RuntimeException {
    private final String messageKey;
    private final Object[] objects;

    public LocalizedRuntimeException(String messageKey) {
        this.messageKey = messageKey;
        this.objects = null;
    }

    public LocalizedRuntimeException(String messageKey, Object... objects) {
        this.messageKey = messageKey;
        this.objects = objects;
    }


    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getObjects() {
        return objects;
    }
}
