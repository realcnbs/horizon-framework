package com.realcnbs.framework.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

@ToString
@Getter
@Setter
public class RestRequestFilter {

    private Integer pageNum = 1;

    private Integer pageSize = 20;

    private String filter;

    private String sort;

    public Map<String, String> getFilterMap() {
        Map<String, String> filterMap = new HashMap<>();
        if (filter != null && filter.contains(":")) {
            for (String token : filter.split(";")) {
                filterMap.put(token.substring(0, token.indexOf(":")), token.substring(token.indexOf(":") + 1));
            }
        }
        return filterMap;
    }

    public void setFilterMap(Map<String, String> filterMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : filterMap.entrySet()) {
            builder.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        filter = builder.toString();
    }

    public void setSortMap(LinkedHashMap<String, Sort.Direction> sortMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Sort.Direction> entry : sortMap.entrySet()) {
            builder.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        sort = builder.toString();
    }

    public LinkedHashMap<String, Sort.Direction> getSortMap() {
        LinkedHashMap<String, Sort.Direction> sortMap = new LinkedHashMap<>();
        if (sort != null && sort.contains(":")) {
            for (String token : sort.split(";")) {
                String[] split = token.split(":");
                if (!split[1].toUpperCase().equals("ASC") && !split[1].toUpperCase().equals("DESC")) {
                    split[1] = "DESC";
                }
                sortMap.put(split[0], Sort.Direction.valueOf(split[1].toUpperCase()));
            }
        }
        return sortMap;
    }

    public Pageable getPageable() {
        PageRequest request;
        if (getSortMap() != null && getSortMap().size() > 0) {
            List<Sort.Order> orders = new ArrayList<>();
            for (Map.Entry<String, Sort.Direction> sortEntry : getSortMap().entrySet()) {
                orders.add(new Sort.Order(sortEntry.getValue(), sortEntry.getKey()));
            }
            request = PageRequest.of(pageNum - 1, pageSize, Sort.by(orders));
        } else {
            request = PageRequest.of(pageNum - 1, pageSize);
        }
        return request;
    }
}
