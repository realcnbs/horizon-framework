package com.realcnbs.horizon.framework;

public abstract class FrameworkRuntimeException extends RuntimeException {

    public FrameworkRuntimeException(String message) {
        super(message);
    }

    public FrameworkRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
