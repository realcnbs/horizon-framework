package com.realcnbs.framework.rest.exception;

import com.realcnbs.framework.FrameworkException;

public class FormException extends FrameworkException {

    public FormException(String message) {
        super(message);
    }

    public FormException(String message, Throwable cause) {
        super(message, cause);
    }
}
