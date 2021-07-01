package com.realcnbs.horizon.framework.data.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;

public class AllowedValue {

    @JsonProperty
    @JsonView(HorizonPublic.class)
    private String name;

    @JsonProperty
    @JsonView(HorizonPublic.class)
    private String value;

    public AllowedValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
