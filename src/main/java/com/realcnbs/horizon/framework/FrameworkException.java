package com.realcnbs.horizon.framework;


public abstract class FrameworkException extends FrameworkRuntimeException {

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
