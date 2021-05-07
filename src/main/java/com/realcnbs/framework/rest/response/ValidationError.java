package com.realcnbs.framework.rest.response;

import com.realcnbs.framework.rest.controller.view.Public;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class ValidationError {

    @JsonProperty
    @JsonView(Public.class)
    private String field;

    @JsonProperty
    @JsonView(Public.class)
    private String code;

    @JsonProperty
    @JsonView(Public.class)
    private String message;

    public ValidationError(String field, String code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }

    public ValidationError() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
