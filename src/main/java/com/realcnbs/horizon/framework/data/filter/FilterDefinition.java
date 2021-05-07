package com.realcnbs.horizon.framework.data.filter;

import com.google.common.base.CaseFormat;

import java.util.List;

public class FilterDefinition {

    private String name;

    private String field;

    private List<AllowedValue> allowedValues;

    private String description;

    private FieldFilter.CheckType checkType;

    private DataType dataType;

    public FilterDefinition(String name, FieldFilter.CheckType checkType, DataType dataType) {
        this.name = name;
        this.checkType = checkType;
        this.dataType = dataType;
        this.field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    public FilterDefinition(String name, String field, FieldFilter.CheckType checkType, DataType dataType) {
        this.name = name;
        this.checkType = checkType;
        this.dataType = dataType;
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

    public List<AllowedValue> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(List<AllowedValue> allowedValues) {
        this.allowedValues = allowedValues;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FieldFilter.CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(FieldFilter.CheckType checkType) {
        this.checkType = checkType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "FilterDefinition{" +
                "name='" + name + '\'' +
                ", field='" + field + '\'' +
                ", allowedValues=" + allowedValues +
                ", description='" + description + '\'' +
                ", checkType=" + checkType +
                ", dataType=" + dataType +
                '}';
    }

    public enum DataType {
        NUMBER, DATE, STRING, BOOLEAN
    }
}


