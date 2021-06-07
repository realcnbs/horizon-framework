package com.realcnbs.horizon.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericResponse implements Response {

    protected String message;

    @Override
    @JsonProperty
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
