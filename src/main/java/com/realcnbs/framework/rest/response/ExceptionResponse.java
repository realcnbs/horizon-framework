package com.realcnbs.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ExceptionResponse extends Response {

    @JsonProperty("errorType")
    String getErrorType();

    void setErrorType(ErrorType type);

    enum ErrorType {
        INTERNAL,
        CONFIGURATION,
        AUTHENTICATION,
        AUTHORIZATION,
        VALIDATION,
        NOT_FOUND,
        INVALID_JSON,
        INVALID_REQUEST
    }
}
