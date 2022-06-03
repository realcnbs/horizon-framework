package com.realcnbs.horizon.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;

public interface ExceptionResponse extends Response {

    @JsonProperty("errorType")
    @JsonView(HorizonPublic.class)
    String getErrorType();

    void setErrorType(ErrorType type);

    enum BaseErrorType implements ErrorType {
        INTERNAL,
        CONFIGURATION,
        AUTHENTICATION,
        AUTH_TOKEN_EXPIRED,
        AUTHORIZATION,
        VALIDATION,
        NOT_FOUND,
        INVALID_JSON,
        INVALID_REQUEST;

        public String enumName(){
            return name();
        }
    }
}
