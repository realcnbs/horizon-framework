package com.realcnbs.horizon.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;
import com.realcnbs.horizon.framework.util.HorizonUtils;

@JsonPropertyOrder({"errorType", "message", "errorRef"})
public class GenericExceptionResponse implements ExceptionResponse {

    private String message;

    private ErrorType type;

    private String errorRef;

    public GenericExceptionResponse() {
        errorRef = HorizonUtils.getRandomString(10).toUpperCase();
    }

    @JsonProperty("errorRef")
    @JsonView(HorizonPublic.class)
    public String getErrorRef() {
        return errorRef;
    }

    @Override
    public String getErrorType() {
        return type != null ? type.name() : "Unknown";
    }

    @Override
    public void setErrorType(ErrorType type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
