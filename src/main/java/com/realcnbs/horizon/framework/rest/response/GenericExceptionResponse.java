package com.realcnbs.horizon.framework.rest.response;

import com.realcnbs.horizon.framework.rest.controller.view.Public;
import com.realcnbs.horizon.framework.util.HashUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;

@JsonPropertyOrder({"errorType", "message", "errorRef"})
public class GenericExceptionResponse implements ExceptionResponse {

    private String message;

    private ErrorType type;

    private String errorRef;

    public GenericExceptionResponse() {
        errorRef = HashUtils.getRandomString(10).toUpperCase();
    }

    @JsonProperty("errorRef")
    @JsonView(Public.class)
    public String getErrorRef() {
        return errorRef;
    }

    @Override
    @JsonView(Public.class)
    public String getErrorType() {
        return type != null ? type.name() : "Unknown";
    }

    @Override
    public void setErrorType(ErrorType type) {
        this.type = type;
    }

    @Override
    @JsonView(Public.class)
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
