package com.realcnbs.framework.data.filter;

public class Limit {

    private Integer offset;

    private Integer limit;

    public Limit() {
    }

    public Limit(Integer limit) {
        this.limit = limit;
        this.offset = 0;
    }

    public Limit(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
