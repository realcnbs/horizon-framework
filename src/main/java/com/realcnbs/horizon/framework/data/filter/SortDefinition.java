package com.realcnbs.horizon.framework.data.filter;

import com.google.common.base.CaseFormat;

public class SortDefinition {

    private String name;

    private String field;

    public SortDefinition(String name) {
        this.name = name;
        this.field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    public SortDefinition(String name, String field) {
        this.name = name;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
