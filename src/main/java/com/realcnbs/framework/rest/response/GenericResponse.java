package com.realcnbs.framework.rest.response;

import com.realcnbs.framework.rest.controller.view.Public;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class GenericResponse implements Response {

    protected String message;

    @Override
    @JsonProperty
    @JsonView(Public.class)
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
