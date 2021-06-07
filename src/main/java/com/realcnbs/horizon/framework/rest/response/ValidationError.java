package com.realcnbs.horizon.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationError {

    @JsonProperty
    private String field;

    @JsonProperty
    private String code;

    @JsonProperty
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
