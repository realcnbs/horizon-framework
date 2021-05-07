package com.realcnbs.horizon.framework.rest.response;

import com.realcnbs.horizon.framework.rest.controller.view.Public;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.Map;

public class ValidationExceptionResponse implements ExceptionResponse {

    private String message = "Submitted data is not valid";

    private Map<String, List<ValidationError>> errors;

    private ErrorType type = ErrorType.VALIDATION;

    @Override
    public String getErrorType() {
        return type.name();
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

    @JsonProperty("errors")
    @JsonView(Public.class)
    public Map<String, List<ValidationError>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<ValidationError>> errors) {
        this.errors = errors;
    }
}
