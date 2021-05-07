package com.realcnbs.framework.rest.exception;

import com.realcnbs.framework.FrameworkException;

public class NotFoundException extends FrameworkException {

    public NotFoundException(String message) {
        super(message);
    }
}
