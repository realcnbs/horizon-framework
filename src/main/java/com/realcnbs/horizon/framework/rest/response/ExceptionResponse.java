package com.realcnbs.horizon.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;

public interface ExceptionResponse extends Response {

    @JsonProperty("errorType")
    @JsonView(HorizonPublic.class)
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
