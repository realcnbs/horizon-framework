package com.realcnbs.horizon.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;

public class GenericResponse implements Response {

    protected String message;

    @Override
    @JsonProperty
    @JsonView(HorizonPublic.class)
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
