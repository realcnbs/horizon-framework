package com.realcnbs.framework.data.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterBuilder {

    private List<FieldFilter> filters = new ArrayList<>();

    private List<Sort> sorts = new ArrayList<>();

    private List<FieldFilter> joinFilters = new ArrayList<>();

    private List<Sort> joinSorts = new ArrayList<>();

    private Limit limit = new Limit();

    public static FilterBuilder instance() {
        return new FilterBuilder();
    }

    public FieldFilterCheckBuilder and(String field) {
        FieldFilter filter = new FieldFilter(field);
        filter.setLogicType(FieldFilter.LogicType.AND);
        filters.add(filter);
        return new FieldFilterCheckBuilder(filter, this);
    }

    public FieldFilterCheckBuilder or(String field) {
        FieldFilter filter = new FieldFilter(field);
        filter.setLogicType(FieldFilter.LogicType.OR);
        filters.add(filter);
        return new FieldFilterCheckBuilder(filter, this);
    }

    public SortDirectionBuilder sort(String field) {
        Sort sort = new Sort(field);
        if (!sorts.contains(sort)) {
            sorts.add(sort);
        }
        return new SortDirectionBuilder(sort, this);
    }

    public FilterBuilder limit(Integer offset, Integer limit) {
        this.limit = new Limit(offset, limit);
        return this;
    }

    public FieldFilterCheckBuilder andJoined(String field) {
        FieldFilter filter = new FieldFilter(field);
        filter.setLogicType(FieldFilter.LogicType.AND);
        joinFilters.add(filter);
        return new FieldFilterCheckBuilder(filter, this);
    }

    public FieldFilterCheckBuilder orJoined(String field) {
        FieldFilter filter = new FieldFilter(field);
        filter.setLogicType(FieldFilter.LogicType.OR);
        joinFilters.add(filter);
        return new FieldFilterCheckBuilder(filter, this);
    }

    public SortDirectionBuilder sortJoined(String field) {
        Sort sort = new Sort(field);
        if (!joinSorts.contains(sort)) {
            joinSorts.add(sort);
        }
        return new SortDirectionBuilder(sort, this);
    }

    public List<FieldFilter> getFieldFilters() {
        return filters;
    }

    public void setFieldFilters(List<FieldFilter> filters) {
        this.filters = filters;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public List<FieldFilter> getJoinFieldFilters() {
        return joinFilters;
    }

    public void setJoinFieldFilters(List<FieldFilter> joinFilters) {
        this.joinFilters = joinFilters;
    }

    public List<Sort> getJoinSorts() {
        return joinSorts;
    }

    public void setJoinSorts(List<Sort> joinSorts) {
        this.joinSorts = joinSorts;
    }

    @Override
    public String toString() {
        return "FilterBuilder{" +
                "filters=" + filters +
                ", joinFilters=" + joinFilters +
                ", joinSorts=" + joinSorts +
                ", sorts=" + sorts +
                ", limit=" + limit +
                '}';
    }

    public class SortDirectionBuilder {

        private final Sort sort;

        private final FilterBuilder filterBuilder;

        SortDirectionBuilder(Sort sort, FilterBuilder filterBuilder) {
            this.sort = sort;
            this.filterBuilder = filterBuilder;
        }

        public FilterBuilder asc() {
            sort.setDirection(Sort.Direction.ASC);
            return filterBuilder;
        }

        public FilterBuilder desc() {
            sort.setDirection(Sort.Direction.DESC);
            return filterBuilder;
        }
    }

    public class FieldFilterCheckBuilder {

        private FieldFilter filter;

        private FilterBuilder filterBuilder;

        FieldFilterCheckBuilder(FieldFilter filter, FilterBuilder filterBuilder) {
            this.filter = filter;
            this.filterBuilder = filterBuilder;
        }

        public FilterBuilder in(List values) {
            filter.setCheckType(FieldFilter.CheckType.IN);
            filter.setValue(values);
            return filterBuilder;
        }

        public FilterBuilder like(Object value) {
            filter.setCheckType(FieldFilter.CheckType.LIKE);
            filter.setValue(value);
            return filterBuilder;
        }

        public FilterBuilder eq(Object value) {
            filter.setCheckType(FieldFilter.CheckType.EQUALS);
            filter.setValue(value);
            return filterBuilder;
        }

        public FilterBuilder neq(Object value) {
            filter.setCheckType(FieldFilter.CheckType.NOT_EQUALS);
            filter.setValue(value);
            return filterBuilder;
        }

        public FilterBuilder lte(Object value) {
            filter.setCheckType(FieldFilter.CheckType.LESS);
            filter.setValue(value);
            return filterBuilder;
        }

        public FilterBuilder gte(Object value) {
            filter.setCheckType(FieldFilter.CheckType.GREATER);
            filter.setValue(value);
            return filterBuilder;
        }

        public FilterBuilder leq(Object value) {
            filter.setCheckType(FieldFilter.CheckType.LESS_EQUALS);
            filter.setValue(value);
            return filterBuilder;
        }

        public FilterBuilder geq(Object value) {
            filter.setCheckType(FieldFilter.CheckType.GREATER_EQUALS);
            filter.setValue(value);
            return filterBuilder;
        }
    }
}
