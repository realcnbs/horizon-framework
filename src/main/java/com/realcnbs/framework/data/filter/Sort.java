package com.realcnbs.framework.data.filter;

import java.util.Objects;

public class Sort {

    private Direction direction;

    private String field;

    public Sort(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public Sort(String field) {
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sort sort = (Sort) o;

        return Objects.equals(field, sort.field);
    }

    @Override
    public int hashCode() {
        return field != null ? field.hashCode() : 0;
    }

    public enum Direction {
        ASC, DESC
    }
}
