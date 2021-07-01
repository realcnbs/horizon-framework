package com.realcnbs.horizon.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;

public class ValidationError {

    @JsonProperty
    @JsonView(HorizonPublic.class)
    private String field;

    @JsonProperty
    @JsonView(HorizonPublic.class)
    private String code;

    @JsonProperty
    @JsonView(HorizonPublic.class)
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
