package com.realcnbs.horizon.framework.data.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllowedValue {

    @JsonProperty
    private String name;

    @JsonProperty
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
