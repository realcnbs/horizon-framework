package com.realcnbs.horizon.framework.rest.repr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.data.filter.FilterDefinition;
import com.realcnbs.horizon.framework.data.filter.SortDefinition;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({"pageNum", "pageSize", "totalElements", "totalPages", "_links"})
public class PagedEntityRepr implements Representation {

    private final Page page;

    private List<Representation> representations;

    private List<FilterDefinition> filters;

    private List<SortDefinition> sorts;

    private Map<String, Sort.Direction> appliedSorts;

    private Map<String, String> appliedFilters;

    public PagedEntityRepr(Page page) {
        this.page = page;
    }

    @JsonProperty("pageMetadata")
    @JsonView(HorizonPublic.class)
    public Map<String, Object> getPageMetadata() {

        Map<String, Object> meta = new HashMap<>();

        meta.put("size", page.getSize());
        meta.put("totalElements", page.getTotalElements());
        meta.put("totalPages", page.getTotalPages());
        meta.put("number", page.getNumber() + 1);
        meta.put("hasNext", page.hasNext());
        meta.put("hasPrevious", page.hasPrevious());
        meta.put("isFirst", page.isFirst());
        meta.put("isLast", page.isLast());

        if (filters != null) {

            List<Map<String, Object>> filters = new ArrayList<>();

            for (FilterDefinition allowedFilter : this.filters) {
                Map<String, Object> filter = new HashMap<>();
                filter.put("name", allowedFilter.getName());
                if (allowedFilter.getAllowedValues() != null) {
                    filter.put("allowedValues", allowedFilter.getAllowedValues());
                }
                if (allowedFilter.getDescription() != null) {
                    filter.put("description", allowedFilter.getDescription());
                }
                filter.put("applied", false);
                if (appliedFilters != null) {
                    for (Map.Entry<String, String> entry : appliedFilters.entrySet()) {
                        if (entry.getKey().equals(allowedFilter.getName())) {
                            filter.put("applied", true);
                            filter.put("appliedValue", entry.getValue());
                        }
                    }
                }
                filters.add(filter);
            }
            meta.put("filters", filters);
        }

        if (sorts != null) {

            List<Map<String, Object>> sorts = new ArrayList<>();
            for (SortDefinition allowedSort : this.sorts) {

                Map<String, Object> sort = new HashMap<>();
                sort.put("name", allowedSort.getName());
                sorts.add(sort);

                if (appliedSorts != null) {
                    sort.put("applied", false);
                    for (Map.Entry<String, Sort.Direction> entry : appliedSorts.entrySet()) {
                        if (entry.getKey().equals(allowedSort.getName())) {
                            sort.put("applied", true);
                            sort.put("direction", entry.getValue().name());
                        }
                    }
                }
            }
            meta.put("sorts", sorts);
        }

        return meta;
    }

    @JsonProperty("content")
    @JsonView(HorizonPublic.class)
    public List<Representation> getRepresentations() {
        return representations;
    }

    public void setRepresentations(List<Representation> representations) {
        this.representations = representations;
    }

    public void setFilters(List<FilterDefinition> filters) {
        this.filters = filters;
    }

    public void setSorts(List<SortDefinition> sorts) {
        this.sorts = sorts;
    }

    public void setAppliedSorts(Map<String, Sort.Direction> appliedSorts) {
        this.appliedSorts = appliedSorts;
    }

    public void setAppliedFilters(Map<String, String> appliedFilters) {
        this.appliedFilters = appliedFilters;
    }
}
