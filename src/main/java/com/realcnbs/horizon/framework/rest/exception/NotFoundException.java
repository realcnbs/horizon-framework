package com.realcnbs.horizon.framework.rest.exception;

import com.realcnbs.horizon.framework.FrameworkException;

public class NotFoundException extends FrameworkException {

    public NotFoundException(String message) {
        super(message);
    }
}
