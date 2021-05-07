package com.realcnbs.framework.data.filter;

import com.realcnbs.framework.FrameworkException;

public class PageOutOfBoundsException extends FrameworkException {

    public PageOutOfBoundsException(String message) {
        super(message);
    }

    public PageOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
