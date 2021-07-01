package com.realcnbs.horizon.framework.data.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class FieldFilter {

    private CheckType checkType;

    @Builder.Default
    private LogicType logicType = LogicType.AND;

    private Object value;

    private String field;

    public FieldFilter() {
        checkType = CheckType.EQUALS;
    }

    public FieldFilter(String field, Object value) {
        this.value = value;
        this.field = field;
        checkType = CheckType.EQUALS;
    }

    public FieldFilter(String field) {
        this.field = field;
        checkType = CheckType.EQUALS;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    public LogicType getLogicType() {
        return logicType;
    }

    public void setLogicType(LogicType logicType) {
        this.logicType = logicType;
    }

    public Object getValue() {
        switch (checkType) {
            case LIKE:
                return "%" + value + "%";
            case STARTS_WITH:
                return "%" + value;
            case ENDS_WITH:
                return value + "%";
            default:
                return value;
        }
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "FieldFilter{" +
                "checkType=" + checkType +
                ", logicType=" + logicType +
                ", value=" + value +
                ", field='" + field + '\'' +
                '}';
    }

    public enum CheckType {
        EQUALS, NOT_EQUALS, LIKE, STARTS_WITH, ENDS_WITH, LESS_EQUALS, GREATER_EQUALS, LESS, GREATER, IN, ARRAY_ANY, ANY_IN_ARRAY
    }

    public enum LogicType {
        AND, OR
    }
}
