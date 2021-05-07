package com.realcnbs.horizon.framework.data.filter;

import com.realcnbs.horizon.framework.FrameworkException;

public class PageOutOfBoundsException extends FrameworkException {

    public PageOutOfBoundsException(String message) {
        super(message);
    }

    public PageOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
