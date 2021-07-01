package com.realcnbs.horizon.framework.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;

public interface Response {

    @JsonProperty("message")
    @JsonView(HorizonPublic.class)
    String getMessage();

    void setMessage(String message);
}
