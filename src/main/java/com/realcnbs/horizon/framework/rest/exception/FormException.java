package com.realcnbs.horizon.framework.rest.exception;

import com.realcnbs.horizon.framework.FrameworkException;

public class FormException extends FrameworkException {

    public FormException(String message) {
        super(message);
    }

    public FormException(String message, Throwable cause) {
        super(message, cause);
    }
}
